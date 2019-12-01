<template>
	<div class="projectDetail">
		<div class="container">
			<div class="title">{{news.title}}</div>
			<div class="info"><span>来源：<span style="padding: 0 20px;">发布时间：{{news.publishDate}}</span></span></div>
			<!--<img class="imgbox" src="../assets/1.jpg" alt="">
			<div class="detail">决定恢复科举时代JFK是地方势力扩大飞机喀什的飞机喀什地方克里斯蒂就</div>-->
			<div style="text-align: center" v-html="news.htmlContent"></div>
		</div>
	</div>
</template>

<script>
    import {getRequest} from '../utils/api'
    export default{
        methods: {
            goBack(){
                this.$router.go(-1);
            }
        },
        mounted: function () {
            let id = this.$route.query.id;
            var _this = this;
            this.loading = true;
            getRequest("/news/findById?newsId=" + id).then(resp=> {
                if (resp.status == 200) {
                    _this.news = resp.data;
                }
                _this.loading = false;
            }, resp=> {
                _this.loading = false;
                _this.$message({type: 'error', message: '页面加载失败!'});
            });
        },
        data(){
            return {
                news:{
                    title:'',
                    newsType:'',
                    state:null,
                    publishDate:null,
                    content:'',
                    htmlContent:'',
                    summary:'',
				},
                loading: false,
            }
        }
    }

</script>

<style lang="less">
.projectDetail {
	.container{
		max-width: 1170px;
		margin: 0 auto;
		.title {
			text-align: center;
			padding: 40px 0;
			font-size: 24px;
			line-height: 1.5;
			text-align: center;
			color: #444;
		}
		.info {
			font-size: 14px;
			text-align: center;
			margin-bottom: 30px;
			padding: 6px 10px;
			color: #777;
			border-bottom: 1px solid #ddd;
		}
		.imgbox {
			width: 100%;
			max-height: 500px;
		}
		.detail {
			padding: 20px 0;
		}
	}
}
</style>
