<template>
    <div class="teamlist">
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
                    label="团队描述"
                    width="300" align="left">
                <template slot-scope="scope"><span style="color: #409eff;cursor: pointer">{{ scope.row.configContent}}</span>
                </template>
            </el-table-column>
            <el-table-column
                    label="创建时间" align="left">
                <template slot-scope="scope">{{ scope.row.createdDate}}</template>
            </el-table-column>
            <el-table-column
                    prop="createdBy"
                    label="创建人"
                    width="120" align="left">
            </el-table-column>
            <el-table-column label="操作" align="left">
                <template slot-scope="scope">
                    <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>
<script>
    import {postRequest} from '../utils/api'
    import {putRequest} from '../utils/api'
    import {deleteRequest} from '../utils/api'
    import {getRequest} from '../utils/api'
    export default {
        mounted() {
            let _this = this;
            _this.loadTeam();
            window.bus.$on('teamTableReload', function () {
                _this.loading = true;
                _this.loadTeam();
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
            };
        },
        methods: {
            searchClick(){
                this.loadTeam();
            },
            handleSelectionChange(){

            },
            handleEdit(index,row){
                this.$router.push({path: '/editTeam', query: {type:'edit',id:row.id}});
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
                    let url = '/config/deleteById?configId=' + id;
                    deleteRequest(url).then(resp=> {
                        if (resp.status == 200) {
                            var data = resp.data;
                            _this.$message({type: data.status, message: data.data});
                            if (data.status == 'success') {
                                _this.loadTeam();
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
                this.loadTeam(currentPage, this.pageSize);
            },
            loadTeam(){
                let url = "/config/getConfigByConfigCode?configCode=companyTeam";
                getRequest(url).then(resp=> {
                    this.loading = false;
                    if (resp.status == 200) {
                        this.news = resp.data;
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
.teamlist .el-table th,
.teamlist .el-table td{
    text-align: center;
}
.teamlist .pageBox {
    text-align: right;
    padding-top: 12px;
    padding-right: 30px;
}
</style>
