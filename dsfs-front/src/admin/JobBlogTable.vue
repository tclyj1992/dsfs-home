<style type="text/css">
  .blog_table_footer {
    display: flex;
    box-sizing: content-box;
    padding-top: 10px;
    padding-bottom: 0px;
    margin-bottom: 0px;
    justify-content: space-between;
  }
</style>
<template>
  <div>
  <div style="display: flex;justify-content: flex-start">
    <el-input
            placeholder="通过标题搜索该分类下的博客..."
            prefix-icon="el-icon-search"
            v-model="keywords" style="width: 400px" size="mini">
    </el-input>
    <el-button type="primary" icon="el-icon-search" size="mini" style="margin-left: 3px" @click="searchClick">搜索
    </el-button>
  </div>
  <!--<div style="width: 100%;height: 1px;background-color: #20a0ff;margin-top: 8px;margin-bottom: 0px"></div>-->
  <el-table
          ref="multipleTable"
          :data="news"
          tooltip-effect="dark"
          style="width: 100%;overflow-x: hidden; overflow-y: hidden;"
          max-height="390"
          @selection-change="handleSelectionChange" v-loading="loading">
    <el-table-column
            type="selection"
            width="35" align="left" v-if="showEdit || showDelete">
    </el-table-column>
    <el-table-column
            label="岗位名称"
            width="300" align="left">
      <template slot-scope="scope"><span style="color: #409eff;cursor: pointer" @click="itemClick(scope.row)">{{ scope.row.jobName}}</span>
      </template>
    </el-table-column>
    <el-table-column
            label="需求人数" width="200" align="left">
      <template slot-scope="scope">{{ scope.row.needAmount}}</template>
    </el-table-column>
    <el-table-column
            label="发布时间" width="200" align="left">
      <template slot-scope="scope">{{ scope.row.publishDate}}</template>
    </el-table-column>
    <el-table-column
            label="创建时间" width="200" align="left">
      <template slot-scope="scope">{{ scope.row.createdDate}}</template>
    </el-table-column>
    <el-table-column
            prop="createdBy"
            label="作者"
            width="120" align="left">
    </el-table-column>
    <el-table-column label="操作" align="left" v-if="showEdit || showDelete">
      <template slot-scope="scope">
        <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)" v-if="showEdit">编辑
        </el-button>
        <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)" v-if="showDelete">删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <div class="blog_table_footer">
    <el-button type="danger" size="mini" style="margin: 0px;" v-show="this.news.length>0 && showDelete"
               :disabled="this.selItems.length==0" @click="deleteMany">批量删除
    </el-button>
    <span></span>
    <el-pagination
            background
            :page-size="pageSize"
            layout="prev, pager, next"
            :total="totalCount" @current-change="currentChange" v-show="this.news.length>0">
    </el-pagination>
  </div>
</div>
</template>

<script>
  import {deleteRequest} from '../utils/api'
  import {getRequest} from '../utils/api'
//  import Vue from 'vue'
//  var bus = new Vue()

  export default{
    data() {
      return {
        news: [],
        selItems: [],
        loading: false,
        currentPage: 1,
        totalCount: -1,
        pageSize: 6,
        keywords: '',
        dustbinData: []
      }
    },
    mounted() {
      var _this = this;
      this.loading = true;
      this.loadBlogs(1, this.pageSize);
      window.bus.$on('JobBlogTableReload', function () {
          _this.loading = true;
          _this.loadBlogs(_this.currentPage, _this.pageSize);
      })
    },
    methods: {
      searchClick(){
        this.loadBlogs(1, this.pageSize);
      },
      itemClick(row){
        this.$router.push({path: '/blogDetail', query: {aid: row.id}})
      },
      deleteMany(){
        var selItems = this.selItems;
        for (var i = 0; i < selItems.length; i++) {
          this.dustbinData.push(selItems[i].id)
        }
        this.deleteToDustBin(selItems[0].state)
      },
      //翻页
      currentChange(currentPage){
        this.currentPage = currentPage;
        this.loading = true;
        this.loadBlogs(currentPage, this.pageSize);
      },
      loadBlogs(page, count){
        var _this = this;
        var url = "/job/getJobList?state=" + this.state + "&pageNum=" + page + "&pageSize=" + count + "&jobName=" + this.keywords;
        getRequest(url).then(resp=> {
          _this.loading = false;
          if (resp.status == 200) {
            _this.news = resp.data.list;
            _this.totalCount = resp.data.total;
          } else {
            _this.$message({type: 'error', message: '数据加载失败!'});
          }
        }, resp=> {
          _this.loading = false;
          if (resp.response.status == 403) {
            _this.$message({type: 'error', message: resp.response.data});
          } else {
            _this.$message({type: 'error', message: '数据加载失败!'});
          }
        }).catch(resp=> {
          //压根没见到服务器
          _this.loading = false;
          _this.$message({type: 'error', message: '数据加载失败!'});
        })
      },
      handleSelectionChange(val) {
        this.selItems = val;
      },
      handleEdit(index, row) {
        this.$router.push({path: '/editJob', query: {from: this.activeName,id:row.id}});
      },
      handleDelete(index, row) {
        this.dustbinData.push(row.id);
        this.deleteToDustBin(row.id);
      },
      deleteToDustBin(id){
        var _this = this;
        this.$confirm('永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          _this.loading = true;
          let url = '/job/deleteById?jobId=' + id;
          deleteRequest(url).then(resp=> {
            if (resp.status == 200) {
              var data = resp.data;
              _this.$message({type: data.status, message: data.data});
              if (data.status == 'success') {
                window.bus.$emit('JobBlogTableReload')//通过选项卡都重新加载数据
              }
            } else {
              _this.$message({type: 'error', message: '删除失败!'});
            }
            _this.loading = false;
            _this.dustbinData = []
          }, resp=> {
            _this.loading = false;
            _this.$message({type: 'error', message: '删除失败!'});
            _this.dustbinData = []
          });
        }).catch(() => {
          _this.$message({
            type: 'info',
            message: '已取消删除'
          });
          _this.dustbinData = []
        });
      }
    },
    props: ['state', 'showEdit', 'showDelete', 'activeName']
  }
</script>
