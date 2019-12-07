<template>
  <div class="projects">
    <div class="contentArea">
		<div class="menu">
			<div class="type">{{type.name}}</div>
			<div :class="['item',{'isActive': acNum==i}]" v-for="(item,i) in menuList" :key="i" @click="getAcNum(item,i)">
				<span>{{item.name}}</span>
			</div>
		</div>
		<div class="content">
			<div style="position: relative">
				<div style="font-weight: bold;font-size: 20px;padding-bottom: 10px">{{menuList[0].name}}:</div>
				<span style="position: absolute;left: 0;top: 30px;">大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就时封就开始了地方上岛咖啡劳动竞赛收快递费大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就临时封就开始了地方上岛咖啡劳动竞赛收快递费就大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就大立科技罚款临时封就开始了地方上岛咖啡劳动竞赛收快递费就就</span>
				<img class="rightImg animated bounceIn" src="../assets/1.jpg" alt="暂无图片"></img>
				<div style="clear: both"></div>
			</div>
			<div class="title">工程案例：</div>
			<div>
				<div class="imgItem" v-for="item in projectList" @click="goDetail(item)">
					<img :src="item.imgUrl" alt="@">
					<div class="innerTip">{{item.name}}</div>
				</div>
			</div>
		</div>
	</div>
  </div>
</template>

<script>
    import {getRequest} from '../utils/api'
export default {
  	name: 'project',
	components: {},
	data(){
		return {
			type: {},
			projectTypeId:1,
            currentPage: 1,
            totalCount: -1,
            pageSize: 6,
			menuList:[],
            ps: [],
			acNum: 0, //默认激活第一个
			projectList:[],
		}
	},
	mounted() {
        //this.getProjectTypeByParentId();

		window.bus.$on('getType', (type) => {
			this.type = type
			this.getProjectTypeByParentId();
			console.log(this.menuList,'-----');
		})
	},
	methods: {
		getAcNum(item,i){
			this.acNum = i
			this.projectTypeId = item.id;
			this.loadProject(this.currentPage,this.pageSize);
		},
        //获取项目第一层级
        getProjectTypeByParentId(){
            let url = "/projectType/getProjectTypeByParentId?parentId="+this.type.id;
            getRequest(url).then(resp=> {
                this.loading = false;
                if (resp.status == 200) {
                    this.menuList = resp.data;
                    this.projectTypeId = this.menuList[0].id;
                    this.loadProject(this.currentPage,this.pageSize);
                } else {
                    this.$message({type: 'error', message: '数据加载失败!'});
                }
            }, resp=> {
                this.loading = false;
                if (resp.response.status == 403) {
                    this.$message({type: 'error', message: resp.response.data});
                } else {
                    this.$message({type: 'error', message: '数据加载失败!'});
                }
            }).catch(resp=> {
                //压根没见到服务器
                this.loading = false;
                this.$message({type: 'error', message: '数据加载失败!'});
            })
        },
        loadProject(page, count){
            let projectTypeId = this.projectTypeId;
            let url = "/project/getProjectList?pageNum=" + page + "&pageSize=" + count + "&projectTypeId=" + projectTypeId;
            getRequest(url).then(resp=> {
                this.loading = false;
                if (resp.status == 200) {
                    this.projectList = resp.data.list;
                    this.totalCount = resp.data.total;
                } else {
                    this.$message({type: 'error', message: '数据加载失败!'});
                }
            }, resp=> {
                this.loading = false;
                if (resp.response.status == 403) {
                    this.$message({type: 'error', message: resp.response.data});
                } else {
                    this.$message({type: 'error', message: '数据加载失败!'});
                }
            }).catch(resp=> {
                //压根没见到服务器
                this.loading = false;
                this.$message({type: 'error', message: '数据加载失败!'});
            })
        },
        goDetail(){
		    this.$router.push({
				path: '/projectDetail',
				query: {}
			})
		}
	}
}
</script>
<style lang="less">
.projects {
	background: url("../assets/pbg.png") no-repeat 0 0;
	padding-top: 300px;
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
			.rightImg {
				width: 350px;
				height: 350px;
				position: absolute;
				right: 10px;
				top: 20px;
			}
			.title{
				font-weight: bold;
				padding: 12px;
				font-size: 20px;
			}
			.imgItem {
				width: 252px;
				height: 184px;
				background-color: cadetblue;
				position: relative;
				float: left;
				margin: 12px;
				cursor: pointer;
				img {
					width: 252px;
					height: 184px;
				}
				.innerTip {
					position: absolute;
					left: 0;
					bottom: 0;
					width: 100%;
					height: 40px;
					background-color: rgba(0,0,0,0.7);
					text-align: center;
					line-height: 40px;
					color: #fff;
					transition: all 0.3s;
				}
			}
			.imgItem:hover {
				.innerTip {
					height: 60px;
				}
			}
		}
	}
	
}
</style>