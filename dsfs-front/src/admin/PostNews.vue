<template>
  <el-container v-loading="loading" class="post-article">
    <el-header class="header">
      <label>新闻标题</label>
      <el-input v-model="article.title" placeholder="请输入标题..." style="width: 400px;margin-left: 10px"></el-input>

      <label>新闻分类</label>
      <el-select v-model="article.newsType" placeholder="请选择">
        <el-option
                v-for="item in newsTypeList"
                :key="item.id"
                :label="item.configContent"
                :value="item.id">
        </el-option>
      </el-select>
    </el-header>
    <el-main class="main">
      <div id="editor">
        <mavon-editor style="height: 100%;width: 100%;" ref=md @imgAdd="imgAdd"
                      @imgDel="imgDel" v-model="article.content"></mavon-editor>
      </div>
      <div style="display: flex;align-items: center;margin-top: 15px;justify-content: flex-end">
        <el-button @click="cancelEdit" v-if="from!=undefined">放弃修改</el-button>
        <template v-if="from==undefined || from=='draft'">
          <el-button @click="saveBlog(0)">保存到草稿箱</el-button>
          <el-button type="primary" @click="saveBlog(1)">发表文章</el-button>
        </template>
        <template v-else="from==post">
          <el-button type="primary" @click="saveBlog(1)">保存修改</el-button>
        </template>
      </div>
    </el-main>
  </el-container>
</template>
<script>
  import {postRequest} from '../utils/api'
  import {putRequest} from '../utils/api'
  import {deleteRequest} from '../utils/api'
  import {getRequest} from '../utils/api'
  import {uploadFileRequest} from '../utils/api'
  // Local Registration
  import {mavonEditor} from 'mavon-editor'
  // 可以通过 mavonEditor.markdownIt 获取解析器markdown-it对象
  import 'mavon-editor/dist/css/index.css'
  import {isNotNullORBlank} from '../utils/utils'

  export default {
    mounted: function () {
      this.loadNewsType();
      var from = this.$route.query.from;
      this.from = from;
      var _this = this;
      if (from != null && from != '' && from != undefined) {
        var id = this.$route.query.id;
        this.id = id;
        this.loading = true;
        getRequest("/news/findById?newsId=" + id).then(resp=> {
          _this.loading = false;
          if (resp.status == 200) {
            _this.article = resp.data;
            var tags = resp.data.tags;
            _this.article.dynamicTags = []
            for (var i = 0; i < tags.length; i++) {
              _this.article.dynamicTags.push(tags[i].tagName)
            }
          } else {
            _this.$message({type: 'error', message: '页面加载失败!'})
          }
        }, resp=> {
          _this.loading = false;
          _this.$message({type: 'error', message: '页面加载失败!'})
        })
      }
    },
    components: {
      mavonEditor
    },
    methods: {
        //获取新闻分类
        loadNewsType(){
            getRequest("/config/getConfigByConfigCode?configCode=newsCategory").then(resp=> {
                this.loading = false;
                if (resp.status == 200) {
                    let res = resp.data;
                    this.newsTypeList = res;
                } else {
                    this.$message({type: 'error', message: '页面加载失败!'})
                }
            }, resp=> {
                this.loading = false;
                this.$message({type: 'error', message: '页面加载失败!'})
            })
        },
      cancelEdit(){
        this.$router.go(-1)
      },
      saveBlog(state){
        if (!(isNotNullORBlank(this.article.title, this.article.content))) {
          this.$message({type: 'error', message: '数据不能为空!'});
          return;
        }
        var _this = this;
        _this.loading = true;
        postRequest("/news/", {
          id: _this.article.id,
          title: _this.article.title,
          content: _this.article.content,
          htmlContent: _this.$refs.md.d_render,
          newsType: _this.article.newsType,
          state: state
        }).then(resp=> {
          _this.loading = false;
          if (resp.status == 200 && resp.data.status == 'success') {
            _this.article.id = resp.data.msg;
            _this.$message({type: 'success', message: state == 0 ? '保存成功!' : '发布成功!'});
//            if (_this.from != undefined) {
            window.bus.$emit('blogTableReload')
//            }
            if (state == 1) {
              _this.$router.replace({path: '/newsList'});
            }
          }
        }, resp=> {
          _this.loading = false;
          _this.$message({type: 'error', message: state == 0 ? '保存草稿失败!' : '博客发布失败!'});
        })
      },
      imgAdd(pos, $file){
        var _this = this;
        // 第一步.将图片上传到服务器.
        var formdata = new FormData();
        formdata.append('image', $file);
        uploadFileRequest("/news/uploadimg", formdata).then(resp=> {
          var json = resp.data;
          if (json.status == 'success') {
//            _this.$refs.md.$imgUpdateByUrl(pos, json.msg)
            _this.$refs.md.$imglst2Url([[pos, json.data]])
          } else {
            _this.$message({type: json.status, message: json.msg});
          }
        });
      },
      imgDel(pos){

      },

    },
    data() {
      return {
        categories: [],
        tagInputVisible: false,
        tagValue: '',
        loading: false,
        from: '',
        newsTypeList:[],
        article: {
          id: '-1',
          dynamicTags: [],
          title: '',
          content: '',
          newsType:null,
        }
      }
    }
  }
</script>
<style>
  .post-article > .main > #editor {
    width: 100%;
    height: 450px;
    text-align: left;
  }

  .post-article > .header {
    background-color: #ececec;
    margin-top: 10px;
    padding-left: 5px;
    display: flex;
    justify-content: flex-start;
  }

  .post-article > .main {
    /*justify-content: flex-start;*/
    display: flex;
    flex-direction: column;
    padding-left: 5px;
    background-color: #ececec;
    padding-top: 0px;
  }

  .post-article > .header > .el-tag + .el-tag {
    margin-left: 10px;
  }

  .post-article > .header > .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }

  .post-article > .header > .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }

  .post-article {
  }
</style>
