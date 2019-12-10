<template>
    <div class="projectlist">
        <div class="search">
            <span>项目分类：</span>
            <el-cascader class="cascader" v-model="projectTypeId" :options="options" clearable></el-cascader>
            <el-input
                    placeholder="通过项目名称搜索该分类下的项目..."
                    prefix-icon="el-icon-search"
                    v-model="keywords" style="width: 400px" size="mini">
            </el-input>
            <el-button type="primary" icon="el-icon-search" size="mini" style="margin-left: 3px" @click="searchClick">搜索
            </el-button>
        </div>
        <div style="width: 100%;height: 1px;background-color: #20a0ff;margin-top: 8px;margin-bottom: 0px"></div>
        <el-table
                ref="singleTable"
                :data="news"
                tooltip-effect="dark"
                style="width: 100%;overflow-x: hidden; overflow-y: hidden;"
                max-height="390"
                @selection-change="handleSelectionChange" v-loading="loading">
            <el-table-column
                    type="index"
                    width="50">
            </el-table-column>
            <el-table-column
                    label="项目名称"
                    width="300" align="left">
                <template slot-scope="scope"><span style="color: #409eff;cursor: pointer">{{ scope.row.name}}</span>
                </template>
            </el-table-column>
            <el-table-column
                    label="项目分类"
                    width="300" align="left">
                <template slot-scope="scope"><span style="color: #409eff;cursor: pointer">{{ scope.row.projectTypeName}}</span>
                </template>
            </el-table-column>
            <el-table-column
                    label="创建时间" align="left">
                <template slot-scope="scope">{{ scope.row.createdDate}}</template>
            </el-table-column>
            <el-table-column
                    prop="createdBy"
                    label="作者"
                    width="120" align="left">
            </el-table-column>
            <el-table-column label="操作" align="left">
                <template slot-scope="scope">
                    <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="pageBox">
            <!--<el-button type="danger" size="mini" style="margin: 0px;" @click="deleteMany">批量删除</el-button>-->
            <el-pagination
                    background
                    :page-size="pageSize"
                    layout="prev, pager, next"
                    :total="totalCount"
                    @current-change="currentChange"
                    v-show="this.news.length>0">
            </el-pagination>
        </div>
    </div>
</template>
<script>
    import {postRequest} from '../utils/api'
    import {putRequest} from '../utils/api'
    import {deleteRequest} from '../utils/api'
    import {getRequest} from '../utils/api'
    import JobBlogTable from './JobBlogTable'
    export default {
        mounted() {
            let _this = this;
            _this.loadProject(1,_this.pageSize);
            window.bus.$on('projectTableReload', function () {
                _this.loading = true;
                _this.loadProject(_this.currentPage, _this.pageSize);
            })
        },
        data() {
            return {
                news:[],
                loading: false,
                currentPage: 1,
                totalCount: -1,
                pageSize: 6,
                keywords: '',
                projectTypeId:[-1],
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
            searchClick(){
                this.loadProject(1, this.pageSize);
            },
            handleSelectionChange(){

            },
            handleEdit(index,row){
                this.$router.push({path: '/editProject', query: {type:'edit',id:row.id}});
            },
            handleDelete(index, row) {
                this.deleteToDustBin(row.id);
            },
            deleteToDustBin(id){
                var _this = this;
                this.$confirm('永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    _this.loading = true;
                    let url = '/project/deleteById?projectId=' + id;
                    deleteRequest(url).then(resp=> {
                        if (resp.status == 200) {
                            var data = resp.data;
                            _this.$message({type: data.status, message: data.data});
                            if (data.status == 'success') {
                                _this.loadProject(_this.currentPage,_this.pageSize);
                            }
                        } else {
                            _this.$message({type: 'error', message: '删除失败!'});
                        }
                        _this.loading = false;
                    }, resp=> {
                        _this.loading = false;
                        _this.$message({type: 'error', message: '删除失败!'});
                    });
                }).catch(() => {
                    _this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            currentChange(currentPage){
                this.currentPage = currentPage;
                this.loading = true;
                this.loadProject(currentPage, this.pageSize);
            },
            loadProject(page, count){
                let projectTypeId = -1;
                if(this.projectTypeId && this.projectTypeId.length >0){
                    projectTypeId = this.projectTypeId[this.projectTypeId.length -1];
                }
                let url = "/project/getProjectList?pageNum=" + page + "&pageSize=" + count + "&name=" + this.keywords + "&projectTypeId=" + projectTypeId;
                getRequest(url).then(resp=> {
                    this.loading = false;
                    if (resp.status == 200) {
                        this.news = resp.data.list;
                        this.totalCount = resp.data.total;
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
        },
        components: {
        }
    };
</script>
<style>
.search {
    padding: 24px 12px;
    text-align: left;
}
.search .el-cascader .el-input .el-input__inner,
.search .el-input--mini .el-input__inner{
    height: 30px;
}
.search .cascader {
    margin-right: 30px;
}
.projectlist .el-table th,
.projectlist .el-table td{
    text-align: center;
}
.projectlist .pageBox {
    text-align: right;
    padding-top: 12px;
    padding-right: 30px;
}
</style>
