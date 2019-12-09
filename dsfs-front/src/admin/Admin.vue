<template>
    <el-container class="home_container">
        <el-header>
            <div class="home_title">成都鼎盛防水管理系统</div>
            <div class="home_userinfoContainer">
                <el-dropdown @command="handleCommand">
  <span class="el-dropdown-link home_userinfo">
    {{currentUserName}}<i class="el-icon-arrow-down el-icon--right home_userinfo"></i>
  </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="sysMsg">系统消息</el-dropdown-item>
                        <el-dropdown-item command="MyArticle">我的文章</el-dropdown-item>
                        <el-dropdown-item command="MyHome">个人主页</el-dropdown-item>
                        <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </el-header>
        <el-container>
            <el-aside width="200px">
                <el-menu
                        default-active="0"
                        class="el-menu-vertical-demo" style="background-color: #ECECEC" router>
                    <template v-for="(item,index) in myRoute" v-if="!item.hidden">
                        <el-submenu :index="index+''" v-if="item.children.length>1" :key="index">
                            <template slot="title">
                                <i :class="item.iconCls"></i>
                                <span>{{item.name}}</span>
                            </template>
                            <el-menu-item v-for="child in item.children" v-if="!child.hidden" :index="child.path"
                                          :key="child.path">
                                {{child.name}}
                            </el-menu-item>
                        </el-submenu>
                        <template v-else>
                            <el-menu-item :index="item.children[0].path">
                                <i :class="item.children[0].iconCls"></i>
                                <span slot="title">{{item.children[0].name}}</span>
                            </el-menu-item>
                        </template>
                    </template>
                </el-menu>
            </el-aside>
            <el-container>
                <el-main>
                    <el-breadcrumb separator-class="el-icon-arrow-right">
                        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                        <el-breadcrumb-item v-text="this.$router.currentRoute.name"></el-breadcrumb-item>
                    </el-breadcrumb>
                    <keep-alive>
                        <router-view v-if="this.$route.meta.keepAlive"></router-view>
                    </keep-alive>
                    <router-view v-if="!this.$route.meta.keepAlive"></router-view>
                </el-main>
            </el-container>
        </el-container>
    </el-container>
</template>
<script>
    import {getRequest} from '../utils/api'
    import Login from './Login'
    import Company from './Company'
    import NewsList from './NewsList'
    import PostNews from './PostNews'
    import UserMana from './UserMana'
    import Admin from './Admin'
    import Address from './Address'
    import JobList from './JobList'
    import PostJob from './PostJob'
    import ProjectList from './ProjectList'
    import AddProject from './AddProject'
    import AddProjectType from './AddProjectType'
    import Leader from './Leader'
    import TeamList from './TeamList'
    import AddTeam from './AddTeam'
    import ProjectTypeList from './ProjectTypeList'

    export default {
        methods: {
            handleCommand(command) {
                var _this = this;
                if (command == 'logout') {
                    this.$confirm('注销登录吗?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        getRequest("/logout")
                        _this.currentUserName = '游客';
                        _this.$router.replace({path: '/'});
                    }, function () {
                        //取消
                    })
                }
            }
        },
        mounted: function () {
            this.$alert('为了确保所有的小伙伴都能看到完整的数据演示，数据库只开放了查询权限和部分字段的更新权限，其他权限都不具备，完整权限的演示需要大家在自己本地部署后，换一个正常的数据库用户后即可查看，这点请大家悉知!', '友情提示', {
                confirmButtonText: '确定',
                callback: action => {
                }
            });
            var _this = this;
            getRequest("/currentUserName").then(function (msg) {
                _this.currentUserName = msg.data;
            }, function (msg) {
                _this.currentUserName = '游客';
            });
        },
        data() {
            return {
                currentUserName: '',
                myRoute: [{
                    path: '/login',
                    name: '登录',
                    hidden: true,
                    component: Login
                }, {
                    path: '/admin',
                    name: '',
                    component: Admin,
                    hidden: true
                }, {
                    path: '/admin',
                    component: Admin,
                    name: '公司信息',
                    children: [
                        {
                            path: '/company',
                            iconCls: 'fa fa-user-o',
                            name: '公司简介',
                            component: Company,
                            meta: {
                                keepAlive: true
                            }
                        },{
                            path: '/teamList',
                            name: '团队列表',
                            component: TeamList,
                            meta: {
                                keepAlive: false
                            }
                        }, {
                            path: '/addTeam',
                            name: '添加团队',
                            component: AddTeam,
                            meta: {
                                keepAlive: false
                            }
                        },{
                            path: '/editTeam',
                            name: '编辑团队',
                            component: AddTeam,
                            hidden: true,
                            meta: {
                                keepAlive: false
                            }
                        }, {
                            path: '/leader',
                            name: '领导寄语',
                            component: Leader,
                            meta: {
                                keepAlive: false
                            }
                        }
                    ],
                    meta: {
                        requireAuth: true
                    }
                },{
                    path: '/admin',
                    component: Admin,
                    name: '新闻管理',
                    iconCls: 'fa fa-file-text-o',
                    children: [
                        {
                            path: '/newsList',
                            name: '新闻列表',
                            component: NewsList,
                            meta: {
                                keepAlive: true
                            }
                        }, {
                            path: '/postNews',
                            name: '发表新闻',
                            component: PostNews,
                            meta: {
                                keepAlive: false
                            }
                        }, {
                            path: '/editNews',
                            name: '编辑新闻',
                            component: PostNews,
                            hidden: true,
                            meta: {
                                keepAlive: false
                            }
                        }
                    ],
                    meta: {
                        requireAuth: true
                    }
                },{
                    path: '/admin',
                    component: Admin,
                    name: '项目管理',
                    iconCls: 'fa fa-file-text-o',
                    children: [
                        {
                            path: '/projectList',
                            name: '项目列表',
                            component: ProjectList,
                            meta: {
                                keepAlive: true
                            }
                        }, {
                            path: '/addProject',
                            name: '添加项目',
                            component: AddProject,
                            meta: {
                                keepAlive: false
                            }
                        }, {
                            path: '/editProject',
                            name: '编辑项目',
                            component: AddProject,
                            hidden: true,
                            meta: {
                                keepAlive: false
                            }
                        },{
                            path: '/projectTypeList',
                            name: '项目分类列表',
                            component: ProjectTypeList,
                            meta: {
                                keepAlive: true
                            }
                        },{
                            path: '/editProjectType',
                            name: '编辑项目分类',
                            component: AddProjectType,
                            hidden: true,
                            meta: {
                                keepAlive: false
                            }
                        }
                    ],
                    meta: {
                        requireAuth: true
                    }
                }, {
                    path: '/admin',
                    component: Admin,
                    name: '招聘管理',
                    iconCls: 'fa fa-file-text-o',
                    children: [
                        {
                            path: '/jobList',
                            name: '职位列表',
                            component: JobList,
                            meta: {
                                keepAlive: true
                            }
                        }, {
                            path: '/postJob',
                            name: '添加职位',
                            component: PostJob,
                            meta: {
                                keepAlive: false
                            }
                        }, {
                            path: '/editJob',
                            name: '编辑职位',
                            component: PostJob,
                            hidden: true,
                            meta: {
                                keepAlive: false
                            }
                        }
                    ],
                    meta: {
                        requireAuth: true
                    }
                },{
                    path: '/admin',
                    component: Admin,
                    name: '联系地址',
                    children: [
                        {
                            path: '/address',
                            iconCls: 'fa fa-user-o',
                            name: '联系地址',
                            component: Address
                        }
                    ],
                    meta: {
                        requireAuth: true
                    }
                }, {
                    path: '/admin',
                    component: Admin,
                    name: '用户管理',
                    children: [
                        {
                            path: '/user',
                            iconCls: 'fa fa-user-o',
                            name: '用户管理',
                            component: UserMana
                        }
                    ],
                    meta: {
                        requireAuth: true
                    }
                }]
            }
        }
    }
</script>
<style>
    .home_container {
        height: 100%;
        position: absolute;
        top: 0px;
        left: 0px;
        width: 100%;
    }

    .el-header {
        background-color: #20a0ff;
        color: #333;
        text-align: center;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .el-aside {
        background-color: #ECECEC;
    }

    .el-main {
        background-color: #fff;
        color: #000;
        text-align: center;
    }

    .home_title {
        color: #fff;
        font-size: 22px;
        display: inline;
    }

    .home_userinfo {
        color: #fff;
        cursor: pointer;
    }

    .home_userinfoContainer {
        display: inline;
        margin-right: 20px;
    }
</style>
