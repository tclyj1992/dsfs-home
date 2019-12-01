<template>
  <div class="about">
	<div class="topContent"></div>
    <div class="address">
		<div class="title"><span class="innerT">我们的地址</span></div>
		<div class="content">
			<div class="right">
				<div>企业：{{infos.companyName}}</div>
				<div>邮箱：{{infos.email}}</div>
				<div>电话：{{infos.phoneNumber}}</div>
				<div>地址：{{infos.address}}</div>
			</div>
			<div class="left">
				<div class="img"></div>
			</div>
		</div>
	</div>
  </div>
</template>

<script>
    import {getRequest} from '../utils/api'
	export default {
		name: 'about',
		components: {},
		data(){
			return {
				infos: {}
			}
		},
		created(){
			this.getData();
		},
		methods: {
			getData(){
                getRequest("/contact/get").then(resp=> {
                    this.loading = false;
                    if (resp.status == 200) {
                        this.infos = resp.data;
                    } else {
                        this.$message({type: 'error', message: '页面加载失败!'})
                    }
                }, resp=> {
                    this.loading = false;
                    this.$message({type: 'error', message: '页面加载失败!'})
                });
			}
		}
	}
</script>
<style lang="less">
	.about {
		background-size: cover;
		.topContent {
			//height: 400px;
			//background-color: skyblue;
			height: 380px;
			background: url(../assets/contactUs.png) no-repeat center center;
			background-size: 100% 100%;
		}
		.title {
			font-size: 30px;
			font-weight: bold;
			color: #00a3e4;
			border-bottom: 1px solid #e8e8e8;
			box-shadow: 0px 0px 5px #e8e8e8;
			text-shadow: 5px 5px 5px #e8e8e8;
			background-color: #00a3e4;
			.innerT {
				display: inline-block;
				height: 80px;
				line-height: 80px;
				background-color: #f3f3f3;
				padding: 12px;
				margin-left: 200px;
			}
		}
		.content {
			margin-top: 20px;
			display: flex;
			.left {
				width: 50%;
				.img {
					height: 302px;
					background: url(../assets/address.png) no-repeat 0 0;
					background-size: 100% 100%;
				}
				margin-right: 200px;
			}
			.right {
				flex-grow: 1;
				padding: 20px 30px;
				padding-left: 200px;
				padding-top: 40px;
				div{
					line-height: 32px;
				}
			}
		}
	}
</style>
