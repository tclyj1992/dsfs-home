<template>
  <div class="news">
    <div class="contentArea">
      <div class="menu">
        <div class="type">新闻资讯</div>
        <div :class="['item',{'isActive': acNum==i}]" v-for="(item,i) in menuList" :key="i" @click="getAcNum(item,i)">
          <span>{{item.configContent}}</span>
        </div>
      </div>
      <div class="content">
        <div class="title">新闻列表：</div>
        <div class="newsItem" v-for="item in news">
          <div class="newsTitle">{{item.title}}</div>
          <div class="newsDetail">{{item.summary}}</div>
          <div class="newsTime">{{item.publishDate}}</div>
          <div class="newsBtn" @click="goDetail(item)">详情</div>
        </div>
      </div>
    </div>
    <div class="pagenation">
      <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[10, 20, 50]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
    import {getRequest} from '../utils/api';
    export default {
        name: 'news',
        components: {},
        data(){
            return {
                type: 0,// 0:公司新闻；1.行业新闻
                menuList:[],
                ps: [],
                currentPage: 1,
                pageSize: 10,
                total: 0,
                acNum: 0,//默认激活第一个
                news: [],
                newsType:null,
            }
        },
        mounted() {
            this.loadNewsType();
            window.bus.$on('getType', (type) => {
                this.type = type
            })
        },
        methods: {
            //获取新闻分类
            loadNewsType(){
                getRequest("/config/getConfigByConfigCode?configCode=newsCategory").then(resp=> {
                    this.loading = false;
                    if (resp.status == 200) {
                        let res = resp.data;
                        this.menuList =res;
                        this.newsType = this.menuList[0].id;
                        this.loadBlogs(1, this.pageSize);
                    } else {
                        this.$message({type: 'error', message: '页面加载失败!'})
                    }
                }, resp=> {
                    this.loading = false;
                    this.$message({type: 'error', message: '页面加载失败!'})
                })
            },

            loadBlogs(page, count){
                let _this = this;
                let url = "/news/findAllNews?state=1"+"&pageNum=" + page + "&pageSize=" + count +"&newsType=" + this.newsType;
                getRequest(url).then(resp=> {
                    _this.loading = false;
                    if (resp.status == 200) {
                        _this.news = resp.data.list;
                        _this.total = resp.data.total;
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
            getAcNum(item,i){
                console.log(item);
                this.acNum = i
                this.newsType = item.id;
                this.loadBlogs(this.currentPage,this.pageSize);
            },
            goDetail(item){
                this.$router.push({
                    path: '/newsDetail',
                    query: {id:item.id}
                })
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.getJobList();
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.getJobList();
                console.log(`当前页: ${val}`);
            }
        }
    }
</script>
<style lang="less">
  .news {
    background: url("../assets/pbg.png") no-repeat 0 0;
    padding-top: 300px;
    .pagenation {
      padding: 20px 18%;
      text-align: right;
    }
    .contentArea{
      max-width: 1170px;
      margin: 0 auto;
      display: flex;
      .menu {
        .type {
          width: 240px;
          height: 112px;
          line-height: 112px;
          text-align: center;
          font-size: 20px;
          color: #fff;
          background-color: #00a3e4;

        }
        .item {
          width: 100%;
          height: 60px;
          line-height: 60px;
          text-align: center;
          span {
            padding:0 40px;
            padding-bottom: 20px;
            border-bottom: 1px solid #e8e8e8;
          }
        }
        .item:hover {
          cursor: pointer;
          color: #00a3e4;
          background-color: rgba(0,163,228,0.4);
        }
        .isActive {
          color: #00a3e4;
          font-weight: bold;
        }
      }
      .content {
        flex: 1;
        padding-top: 115px;
        .title{
          font-weight: bold;
          padding: 12px;
          font-size: 20px;
        }
        .newsItem {
          width: 100%;
          border-bottom: 1px solid #e8e8e8;
          position: relative;
          padding: 15px;
          .newsTitle {
            color: #4a4a4a;
            font-size: 18px;
            line-height: 36px;
            padding-bottom: 10px;
          }
          .newsDetail {
            line-height: 25px;
            color: #888;
            max-height: 50px;
            height: 50px;
            overflow: hidden;
            font-size: 14px;
          }
          .newsTime {
            font-size: 13px;
            color: #aaa;
          }
          .newsBtn {
            position: absolute;
            right: 15px;
            bottom: 15px;
            display: inline-block;
            width: 68px;
            height: 30px;
            line-height: 30px;
            text-align: center;
            color: #fff;
            background-color: #00a3e4;
            cursor: pointer;
          }
        }
      }
    }

  }
</style>