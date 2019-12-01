<template>
    <div style="text-align: left;margin-top:20px;">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="项目名称" prop="name">
                <el-input v-model="ruleForm.name"></el-input>
            </el-form-item>

            <el-form-item label="项目分类" prop="projectType">
                <el-cascader class="cascader" v-model="ruleForm.projectType" :options="options" clearable></el-cascader>
            </el-form-item>

            <el-form-item label="项目简介" prop="content">
                <el-input type="textarea"
                          :rows="5"
                          v-model="ruleForm.content"></el-input>
            </el-form-item>
            <el-form-item label="项目图片" prop="region">
                <el-upload
                        action="/dsfs-server/news/uploadimg"
                        list-type="picture-card"
                        :auto-upload="true"
                        :on-success="handleSuccess"
                        :on-preview="handlePictureCardPreview"
                        :on-remove="handleRemove"
                        :limit="1"
                        :file-list="fileList">
                    <i class="el-icon-plus"></i>
                </el-upload>
                <el-dialog :visible.sync="dialogVisible">
                    <img width="100%" :src="dialogImageUrl" alt="">
                </el-dialog>
            </el-form-item>
            <el-form-item>
                <el-button @click="cancelEdit" v-if="type=='edit'">放弃修改</el-button>
                <el-button type="primary" @click="submitForm('ruleForm')">保存更新</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    import {postRequest} from '../utils/api'
    import {putRequest} from '../utils/api'
    import {deleteRequest} from '../utils/api'
    import {getRequest} from '../utils/api'
    export default {
        mounted() {
            let type = this.$route.query.type;
            var _this = this;
            if (type=='edit') {
                let id = this.$route.query.id;
                this.type = type;
                this.loading = true;
                getRequest("/project/findById?projectId=" + id).then(resp=> {
                    _this.loading = false;
                    if (resp.status == 200) {
                        _this.ruleForm = resp.data;
                        if(resp.data.imgUrl){
                            this.fileList =[{url: resp.data.imgUrl}];
                        }
                    } else {
                        _this.$message({type: 'error', message: '页面加载失败!'})
                    }
                }, resp=> {
                    _this.loading = false;
                    _this.$message({type: 'error', message: '页面加载失败!'})
                })
            }
            //this.loadCompany();
        },
        data() {
            return {
                type:null,
                ruleForm: {
                    id: -1,
                    name: '',
                    content: '',
                    projectType:[],
                    projectTypeId:null,
                    imgUrl: '',
                },
                fileList:[],
                dialogImageUrl: '',
                dialogVisible: false,
                disabled: false,
                rules: {
                    configContent: [
                        { required: true, message: '请输入活动名称', trigger: 'blur' },
                        { min: 3, max: 1000, message: '长度在 3 到 1000 个字符', trigger: 'blur' }
                    ]
                },
                loading:null,
                options: [{
                    value: 1,
                    label: '防水',
                    children: [{
                        value: 5,
                        label: '屋面防水'
                    },{
                        value: 6,
                        label: '室内防水'
                    },{
                        value: 7,
                        label: '地下室防水'
                    },{
                        value: 8,
                        label: '隧道防水'
                    }]
                },{
                    value: 2,
                    label: '保温',
                    children: [{
                        value: 9,
                        label: '外墙保温'
                    },{
                        value: 10,
                        label: '内墙保温'
                    },{
                        value: 11,
                        label: '装饰一体板保温'
                    },{
                        value: 12,
                        label: '管道保温'
                    }]
                },{
                    value: 3,
                    label: '装饰',
                },{
                    value: 4,
                    label: '维修',
                }]
            };
        },
        methods: {
            loadCompany(){
                getRequest("/config/getConfigByConfigCode?configCode=companyDesc").then(resp=> {
                    this.loading = false;
                    if (resp.status == 200) {
                        let res = resp.data[0];
                        this.ruleForm =res;
                        if(res.imgUrl){
                            this.fileList =[{url: res.imgUrl}];
                        }
                    } else {
                        this.$message({type: 'error', message: '页面加载失败!'})
                    }
                }, resp=> {
                    this.loading = false;
                    this.$message({type: 'error', message: '页面加载失败!'})
                })
            },
            cancelEdit(){
                this.$router.go(-1)
            },
            submitForm(formName) {
                let projectTypeId = -1;
                if(this.ruleForm.projectType && this.ruleForm.projectType.length >0){
                    projectTypeId = this.ruleForm.projectType[this.ruleForm.projectType.length -1];
                }
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        postRequest("/project/saveProject", {
                            id: this.ruleForm.id,
                            name: this.ruleForm.name,
                            projectTypeId: projectTypeId,
                            content: this.ruleForm.content,
                            imgUrl: this.ruleForm.imgUrl
                        }).then(resp=> {
                            this.loading = false;
                            if (resp.status == 200 && resp.data.status == 'success') {
                                this.$message({type: 'success', message:'保存成功!'});
                                window.bus.$emit('projectTableReload')
                                this.$router.replace({path: '/projectList'});
                            }
                        }, resp=> {
                            this.loading = false;
                            this.$message({type: 'error', message: '保存失败!'});
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            handleSuccess(response, file, fileList){
                this.ruleForm.imgUrl = response.data;
                console.log(response)
                console.log(file)
                console.log(fileList)
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            handleRemove(file) {
                console.log(file);
            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
            },
            handleDownload(file) {
                console.log(file);
            }
        },
        components: {

        }
    };
</script>
<style>

</style>
