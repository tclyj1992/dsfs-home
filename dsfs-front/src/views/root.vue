<template>
    <div class="root">
        <div class="nav">
            <div class="l"><i class="icon"></i><span class="title">成都鼎盛防水装饰工程有限公司</span></div>
            <div class="r">
                <ul class="menu">
                    <li class="item"><router-link to="/home">首页</router-link></li>
                    <li class="item"><router-link to='/project'>
                        公司项目
                        <ul class="list">
                            <li @click="goProject(item)" v-for="item in projectTypeList">{{item.name}}</li>
                        </ul>
                    </router-link></li>
                    <li class="item"><router-link to="/companyInfo">关于我们</router-link></li>
                    <li class="item"><router-link to='/news'>新闻资讯</router-link></li>
                    <li class="item"><router-link to='/jobs'>招聘信息</router-link></li>
                    <li class="item"><router-link to='/about'>联系我们</router-link></li>
                </ul>
            </div>
        </div>
		<router-view class="contentBox"/>
        <div class="footer">
            <div class="mark">成都鼎盛防水装饰工程有限公司版权所有</div>
        </div>
    </div>
</template>
<script>
    import {getRequest} from '../utils/api'
	export default {
		data(){
			return {
				projectTypeList:[],
			}
		},
        created(){
            this.getFirstProjectType();
        },
        mounted(){

        },
		methods: {
			goProject(type){
				window.bus.$emit('getType',type)
			},
            //获取项目第一层级
            getFirstProjectType(){
                let url = "/projectType/getFirstProjectType";
                getRequest(url).then(resp=> {
                    this.loading = false;
                    if (resp.status == 200) {
                        this.projectTypeList = resp.data;
                        this.goProject(this.projectTypeList[0]);
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
            }
		}
	}
</script>
<style lang="less">
    .root {
        .nav {
            height: 60px;
            display: flex;
            border-bottom: 1px solid #00a3e4;
            .l{
                flex-grow: 1;
                min-width: 300px;
                padding-left: 40px;
                .icon {
                    display: inline-block;
                    width: 56px;
                    padding: 5px 0;
                    height: 50px;
                    background: url(../assets/icons.png) no-repeat 0 5px;
                    float: left;
                }
                .title {
                    display: inline-block;
                    height: 60px;
                    line-height: 60px;
                    float: left;
                    padding-left: 10px;
                    font-size: 20px;
                    font-weight: bold;
                }
            }
            .r {
                width: 600px;
                padding-right: 60px;
                .menu {
                    .item {
                        float: left;
                        width: 100px;
                        line-height: 60px;
                        text-align: center;
                        cursor: pointer;
						position: relative;
                        .router-link-exact-active.router-link-active {
                            color: #00a3e4;
                            font-weight: bold;
                        }
						.list {
							position: absolute;
							left: 0;
							top: 60px;
							padding-top: 13px;
							z-index: 222;
							width: 100%;
							display: none;
							li {
								height: 40px;
								line-height: 40px;
								color: #fff;
								background-color: rgba(0,0,0,0.6);
							}
							li:hover {
								background-color: rgba(0,0,0,0.4);
                                color: #00a3e4;
							}
						}
                    }
                    .item:hover {
                        border-bottom: 2px solid #00a3e4;
                        a {
                            color: #00a3e4;
                        }
						.list {
							display: block;
						}
                    }
                }
            }
        }
        .nav:hover {
            padding: 10px 0;
            transition: all 0.5s;
        }
        .contentBox {
            min-height: calc(100vh - 165px);
        }
        .footer {
            margin-top: 20px;
            background-color: #00a3e4;
            height: 80px;
            padding: 12px 24px;
            .mark{
                text-align: center;
                padding-top: 40px;
            }
        }
    }
</style>
