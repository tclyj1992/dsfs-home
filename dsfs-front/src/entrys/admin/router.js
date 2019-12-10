import Vue from 'vue'
import Router from 'vue-router'
import Root from '../../views/root.vue'
import Home from '../../views/home.vue'
import About from '../../views/about.vue'
import News from '../../views/news.vue'
import Projects from '../../views/projects.vue'
import projectDetail from '../../views/projectDetail.vue'
import Jobs from '../../views/jobs.vue'
import CompanyInfo from '../../views/companyInfo.vue'
import NewsDetail from '../../views/newsDetail.vue'
import Login from '../../admin/Login'
import Admin from '../../admin/Admin'
import NewsList from '../../admin/NewsList'
import PostNews from '../../admin/PostNews'
import UserMana from '../../admin/UserMana'
import Company from '../../admin/Company'
import Address   from '../../admin/Address'
import JobList   from '../../admin/JobList'
import PostJob   from '../../admin/PostJob'
import ProjectList   from '../../admin/ProjectList'
import ProjectTypeList   from '../../admin/ProjectTypeList'
import AddProject   from '../../admin/AddProject'
import AddProjectType   from '../../admin/AddProjectType'
import Leader   from '../../admin/Leader'
import TeamList   from '../../admin/TeamList'
import AddTeam   from '../../admin/AddTeam'

Vue.use(Router)

const router = new Router({
    //mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/',
            redirect: '/admin'
        },
        {
            path: '/login',
            name: '登录',
            hidden: true,
            component: Login
        }, {
            path: '/admin',
            name: '',
            component: Admin,
            hidden: true,
            meta: {
                requireAuth: true
            }
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
                    hidden: false,
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
        }
    ]
})
router.beforeEach((to, from, next) => {
	if (to.meta.requireAuth) {
		if(localStorage.getItem("loginKey")){//登录了
            next();
		}else {//没登录
			next({
				path: 'login',
				query: {
					redirect: to.fullPath
				}
			})
		}
	} else{
		next();
	}
})
export default router