<template>
	<div class="projectDetail">
		<div class="container">
			<div class="title">{{project.name}}</div>
			<div class="info"><span>来源：<span style="padding: 0 20px;">发布时间： {{project.createdDate}}</span></span></div>
			<img class="imgbox" :src="project.imgUrl" alt="">
			<div class="detail">{{project.content}}</div>
		</div>
	</div>
</template>

<script>
    import {getRequest} from '../utils/api'
    export default{
        methods: {
            goBack(){
                this.$router.go(-1);
            },
			init(){
                let id = this.$route.query.id;
                var _this = this;
                this.loading = true;
                getRequest("/project/findById?projectId=" + id).then(resp=> {
                    if (resp.status == 200) {
                        _this.project = resp.data;
                    }
                    _this.loading = false;
                }, resp=> {
                    _this.loading = false;
                    _this.$message({type: 'error', message: '页面加载失败!'});
                });
			},
        },
        mounted: function () {
            this.init();
        },
        data(){
            return {
                project:{
                    "id": null,
                    "name": "",
                    "projectTypeId": null,
                    "content": "",
                    "createdDate": "",
                    "createdBy": "",
                    "imgUrl": "",
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
