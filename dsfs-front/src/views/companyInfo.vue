<template>
	<div class="news">
		<div class="contentArea">
			<div class="menu">
				<div class="type">关于我们</div>
				<div :class="['item',{'isActive': acNum==i}]" v-for="(item,i) in menuList" :key="i" @click="getAcNum(i)">
					<span>{{item.type}}</span>
				</div>
			</div>
			<div class="itemList" v-if="acNum == 0">
				<img class="imgC" :src="company.imgUrl">
				&emsp;&emsp;{{company.configContent}}
				<div style="clear: both;"></div>
			</div>
			<div class="itemList" v-if="acNum==1">
				<el-carousel :interval="4000" type="card" height="300px">
					<el-carousel-item v-for="(item,i) in teamList" :key="i">
						<img :src="item.imgUrl" alt="">
					</el-carousel-item>
				</el-carousel>
			</div>
			<div class="itemList" v-if="acNum==2">
				<div class="leaderSay">
					<div class="l">
						<div class="top">领导寄语：</div>
						<div class="bottom">&emsp;&emsp;{{leader.configContent}}</div>
					</div>
					<div class="r">
						<div class="tip">
							<img :src="leader.imgUrl" alt="@">
							<div class="tipName">总经理：张细元</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
    import {getRequest} from '../utils/api'
    export default {
        name: 'news',
        components: {},
        data(){
            return {
                type: 0,// 0:公司简介；1.员工风貌；2.领导寄语
                menuList:[{type: '公司简介'}, {type: '员工风貌'},{type: '领导寄语'}],
                ps: [],
                acNum: 0, //默认激活第一个
				company:{
					id: null,
					configName: '',
					configCode: '',
					imgUrl: '',
					configContent: '',
				},
				teamList:[],
				leader: {
                	id: null,
                    configName: '',
                    configCode: '',
                    imgUrl: '',
                    configContent: '',
            	},
            }
        },
        mounted() {
            this.loadData(0);
            window.bus.$on('getType', (type) => {
                this.type = type
            })
        },
        methods: {
            getAcNum(i){
                this.acNum = i
				this.loadData(i);
            },

			loadData(i){
				if(i == 0){
				    this.loadCompany();
				}else if(i == 1){
				    this.loadTeam()
				}else if(i == 2){
				    this.loadLeader();
				}
			},
            loadCompany(){
                getRequest("/config/getConfigByConfigCode?configCode=companyDesc").then(resp=> {
                    this.loading = false;
                    if (resp.status == 200) {
                        let res = resp.data[0];
                        this.company =res;
                    } else {
                        this.$message({type: 'error', message: '页面加载失败!'})
                    }
                }, resp=> {
                    this.loading = false;
                    this.$message({type: 'error', message: '页面加载失败!'})
                })
            },
            loadTeam(){
                let url = "/config/getConfigByConfigCode?configCode=companyTeam";
                getRequest(url).then(resp=> {
                    this.loading = false;
                    if (resp.status == 200) {
                        this.teamList = resp.data;
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
            loadLeader(){
                getRequest("/config/getConfigByConfigCode?configCode=leaderWishes").then(resp=> {
                    this.loading = false;
                    if (resp.status == 200) {
                        let res = resp.data[0];
                        this.leader =res;
                    } else {
                        this.$message({type: 'error', message: '页面加载失败!'})
                    }
                }, resp=> {
                    this.loading = false;
                    this.$message({type: 'error', message: '页面加载失败!'})
                })
            },

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
			.itemList {
				width: 100%;
				padding-top: 130px;
				line-height: 30px;
				padding-left: 12px;
				.imgC {
					width: 400px;
					height: 300px;
					float: right;
					margin-left: 12px;
					box-shadow: 1px 1px 4px #383838;
				}
				.leaderSay {
					display: flex;
					.l{
						width: 300px;
						box-shadow: 0 1px 4px #333;
						padding: 12px;
						margin-right: 5px;
						.top {
							font-size: 20px;
							padding-top: 20px;
							padding-bottom: 12px;
							font-weight: bold;
						}
						.bottom{
							color: #383838;
							font-size: 16px;
						}
					}
					.r{
						flex: 1;
						background-color: #ECECEC;
						.tip {
							float: left;
							width: 33%;
							padding: 0 12px;
							box-sizing: border-box;
							background-color: #f7f7f7;
							height: 192px;
							position: relative;
							border: 1px solid #e8e8e8;
							margin-right: 0.11%;
							img{
								width: 100%;
								height: 162px;
							}
							.tipName {
								text-align: center;
								line-height: 30px;
								font-weight: bold;
							}
							.hoverTip {
								position: absolute;
								left: 12px;
								bottom: 0;
								width: calc(100% - 24px);
								display: none;
								color: #fff;
								padding: 12px;
								box-sizing: border-box;
								background-color: rgba(0,0,0,0.5);
								justify-content: center;
								align-items: center;
								transition: all 0.4s;
							}
						}
						.tip:hover  {
							.hoverTip{
								height: 305px;
								display: flex;
							}
						}
					}
				}
			}
		}

	}
</style>