<template>
    <div class="home">
        <div class="banner">
            <el-carousel indicator-position="outside" style="height: 400px">
                <el-carousel-item v-for="(item,i) in homeImgs" :key="i" style="height: 400px">
                    <img :src="item" alt="" style="height: 350px;width: 100%">
                </el-carousel-item>
            </el-carousel>
        </div>
        <div class="container">
            <div class="item">
                <div class="title" style="cursor: pointer;" @click="goProjects()">工程案列 >></div>
                <el-row>
                    <el-col :span='6' v-for="item in projectList" style="text-align: center;">
                        <div class="en-content" @click="goProjectDetail(item)">
                            <div class="en-img">
                                <img :src="item.imgUrl"/>
                            </div>
                            <div class="en-text">
                                <div class="en-text-title">{{item.name}}</div>
                                <div class="en-text-content">{{item.content}}</div>
                                <div class="en-text-more">了解更多</div>
                            </div>
                        </div>
                    </el-col>
                </el-row>

            </div>
            <div class="item">
                <div class="title">公司简介</div>
                <img class="imgC animated bounceIn" :src="company.imgUrl">
                <span style="line-height: 30px;font-size: 16px;font-family: 'Signika Negative', sans-serif;">
				</br>&emsp;&emsp;{{company.configContent}}
			</span>
                <div style="clear: both;"></div>
            </div>
            <div class="item">
                <div class="title">企业文化</div>
                <div class="lineText animated bounceInLeft" style="padding:12px 0;color: #383838;font-weight: bold;">
                    我们只专注一件事情——工程项目管理
                </div>
                <div class="lineText animated bounceInLeft" style="padding-left: 30px;"><span>确保工程无重大安全事故</span></div>
                <div class="lineText animated bounceInLeft" style="padding-left: 60px;"><span>确保工程施工单位无大面积返工</span>
                </div>
                <div class="lineText animated bounceInLeft" style="padding-left: 120px;"><span>确保工程材料及设备无伪劣产品</span>
                </div>
                <div class="lineText animated bounceInLeft" style="padding-left: 180px;"><span>确保工程管理留下痕迹、实施过程可追溯</span>
                </div>
            </div>
            <div class="item">
                <div class="title">员工风貌</div>
                <el-carousel :interval="4000" type="card" height="300px">
                    <el-carousel-item v-for="(item,i) in teamList" :key="i">
                        <img :src="item.imgUrl" alt="">
                    </el-carousel-item>
                </el-carousel>
            </div>
            <!--<div class="item">
                <div class="title">领导寄语</div>
                <div class="leaderSay">
                    <div class="l">
                        <div class="top">领导寄语：</div>
                        <div class="bottom">员工勤奋，创造一个形象好的公司(噼里啪啦总结性的描述性语言)</div>
                    </div>
                    <div class="r">
                        <div class="tip" v-for="v in imgs">
                            <img :src="v" alt="@">
                            <div class="tipName">总经理：XXX</div>
                            <div class="hoverTip"><span>我们只专注一件事情——工程项目管理</span></div>
                        </div>
                    </div>
                </div>
            </div>-->
        </div>

    </div>
</template>

<script>
    import a1 from '../assets/n1.png';
    import a2 from '../assets/n2.png';

    import t1 from '../assets/team/111.jpg';
    import t2 from '../assets/team/222.jpg';
    import t3 from '../assets/team/333.jpg';

    import h1 from '../assets/homeBg.jpg';
    import {getRequest} from '../utils/api'
    export default {
        name: 'home',
        components: {},
        data() {
            return {
                imgs: [a1, a2, a1, a2, a1, a2],
                teamImgs: [t1, t2, t3],
                homeImgs: [h1, h1, h1, h1],
                projectList:[],
                projectTypeList:[],
                teamList:[],
                company:{},
            }
        },
        mounted(){
            this.getFirstProjectType();
            this.loadProjectList();
            this.loadTeam();
            this.loadCompany();
        },
        methods: {
            //获取项目第一层级
            getFirstProjectType(){
                let url = "/projectType/getFirstProjectType";
                getRequest(url).then(resp=> {
                    this.loading = false;
                    if (resp.status == 200) {
                        this.projectTypeList = resp.data;
                        //this.goProject(this.projectTypeList[0]);
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
            goProjects() {
                window.bus.$emit('getType',this.projectTypeList[0])

                this.$router.push({
                    path: '/project'
                })
            },
            goProjectDetail(item) {
                this.$router.push({
                    path: '/projectDetail',
                    query: {id:item.id}
                })
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
            loadProjectList(){
                let url = "/project/getProjectList?pageNum=1&pageSize=8";
                getRequest(url).then(resp=> {
                    this.loading = false;
                    if (resp.status == 200) {
                        this.projectList = resp.data.list;
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
        }
    }
</script>
<style lang="less">
    @media (min-width: 1200px) {
        .container {
            width: 1170px;
        }
    }

    @media (min-width: 992px) {
        .container {
            width: 1170px;
        }
    }

    @media (min-width: 768) {
        .container {
            width: 750px;
        }
    }

    .home {
        .banner {
            height: 400px;
            //padding: 12px 24px;
            text-align: center;
            font-size: 50px;
            font-weight: bold;
            //background: url(../assets/homeBg.jpg) no-repeat 0 0;
            //background-size: 100% 100%;
        }
        .container {
            display: flex;
            flex-direction: column;
            min-height: 500px;
            max-width: 1170px;
            margin: 0 auto;
            .title {
                text-align: center;
                font-size: 24px;
                color: #00a3e4;
                padding: 24px;
                text-shadow: 2px 2px 4px #e8e8e8;
            }
            .item {
                background-color: #fff;
                height: 100%;
                flex-grow: 1;
                padding-top: 24px;
                .en-content {
                    display: inline-block;
                    border: 1px solid #eee;
                    cursor: pointer;
                    margin-bottom: 20px;
                    .en-img {
                        width: 240px;
                        height: 275px;
                        background: url(../assets/info.jpg) no-repeat center center;
                        background-size: 100% 100%;
                        transition: all 0.3s;
                        img {
                            width: 240px;
                            height: 275px;
                        }
                    }
                    .en-text {
                        width: 240px;
                        padding: 5px 10px;
                        box-sizing: border-box;
                        .en-text-title {
                            color: #5a5a5a;
                            padding-bottom: 10px;
                        }
                        .en-text-content {
                            color: #a1a1a1;
                            border-bottom: 1px solid #eee;
                            padding-bottom: 10px;
                        }
                        .en-text-more {
                            color: #888;
                            padding: 5px 0;
                        }
                    }
                }
                .en-content:hover {
                    .en-img {
                        background-size: 150% 150%;
                    }
                    .en-text {
                        background-color: #00a3e4;
                    }
                    .en-text-title,
                    .en-text-more,
                    .en-text-content {
                        color: #fff;
                    }
                }

                .imgC {
                    position: relative;
                    top: 0;
                    float: left;
                    width: 570px;
                    height: 377px;
                    margin-right: 30px;
                    margin-right: 12px;
                    box-shadow: 0px 1px 4px rgba(0, 0, 0, 0.3);
                }
                .lineText {
                    line-height: 40px;
                    font-size: 20px;
                }
                .lineText:hover span {
                    border-left: 5px solid #e70012;
                    padding-left: 12px;
                }
                .el-carousel__item:nth-child(2n) {
                    background-color: #99a9bf;
                }

                .el-carousel__item:nth-child(2n+1) {
                    background-color: #d3dce6;
                }

            }
        }

    }
</style>