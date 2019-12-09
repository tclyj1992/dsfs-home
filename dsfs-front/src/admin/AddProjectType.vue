<template>
    <div style="text-align: left;margin-top:20px;">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="分类名称" prop="name">
                <el-input v-model="ruleForm.name"></el-input>
            </el-form-item>

            <el-form-item label="分类简介" prop="content">
                <el-input type="textarea"
                          :rows="5"
                          v-model="ruleForm.content"></el-input>
            </el-form-item>
            <el-form-item label="分类图片" prop="region">
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
            this.init();
            //this.loadCompany();
        },
        data() {
            return {
                type:null,
                ruleForm: {
                    id: -1,
                    name: '',
                    parentId:'',
                    content: '',
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
            init(){
                let type = this.$route.query.type;
                var _this = this;
                if (type=='edit') {
                    let id = this.$route.query.id;
                    this.type = type;
                    this.loading = true;
                    getRequest("/projectType/findById?projectId=" + id).then(resp=> {
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
            },
            cancelEdit(){
                this.$router.go(-1)
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        postRequest("/projectType/saveProject", {
                            id: this.ruleForm.id,
                            name: this.ruleForm.name,
                            parentId:this.ruleForm.parentId,
                            content: this.ruleForm.content,
                            imgUrl: this.ruleForm.imgUrl
                        }).then(resp=> {
                            this.loading = false;
                            if (resp.status == 200 && resp.data.status == 'success') {
                                this.$message({type: 'success', message:'保存成功!'});
                                window.bus.$emit('projectTypeTableReload')
                                this.$router.replace({path: '/projectTypeList'});
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
                let filePath = file.url.substr(file.url.indexOf("/blogimg"));
                let url = "/news/deleteFile?filePath="+filePath;
                deleteRequest(url).then(resp=> {
                    if (resp.status == 200) {
                        var data = resp.data;
                        this.$message({type: data.status, message: data.data});
                        if (data.status == 'success') {
                            this.$message({type: data.status, message: data.data});
                        }else {
                            this.$message({type: 'error', message: '删除失败!'});
                        }
                    } else {
                        this.$message({type: 'error', message: '删除失败!'});
                    }
                    this.loading = false;
                }, resp=> {
                    this.loading = false;
                    this.$message({type: 'error', message: '删除失败!'});
                });
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
