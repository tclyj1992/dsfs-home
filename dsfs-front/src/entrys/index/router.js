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
import AddProject   from '../../admin/AddProject'
import Leader   from '../../admin/Leader'
import TeamList   from '../../admin/TeamList'
import AddTeam   from '../../admin/AddTeam'

Vue.use(Router)

export default new Router({
    //mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {

            path: '/',
            name: 'root',
            component: Root,
            children: [
                {
                    path: '/',
                    name: '公司首页',
                    component: Home
                },
                {
                    path: '/companyInfo',
                    name: '公司信息',
                    component: CompanyInfo
                },
				{
				    path: '/home',
				    name: '公司信息',
				    component: Home
				},
                {
                    path: '/about',
                    name: 'about',
                    component: About
                },
                {
                    path: '/news',
                    name: 'news',
                    component: News
                },
                {
                    path: '/project',
                    name: 'project',
                    component: Projects
                },
                {
                    path: '/jobs',
                    name: 'jobs',
                    component: Jobs
                },
                {
                    path: '/projectDetail',
                    name: 'projectDetail',
                    component: projectDetail
                },{
                    path: '/newsDetail',
                    name: 'newsDetail',
                    component: NewsDetail
                }
            ]
        }
    ]
})
/*router.beforeEach((to, from, next) => {
	if (to.meta.requireAuth) {
		if(1){//登录了
			console.log(1111)
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
})*/
