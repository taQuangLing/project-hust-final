<template>
    <div class="branch-filter">
        <span class="title">Chi nhánh</span>
        <el-dropdown @command="handleCommandBranch">
            <span class="el-dropdown-link">
                {{ branchSelected.address }}<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item :command="itemBranch" v-for="itemBranch in branchList" :key="itemBranch.id">{{
            itemBranch.address }}</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    props: ['branchSelectedId'],
    data() {
        return {
            branchList: [
            ],
        }
    },
    computed: {
        branchSelected() {
            if (this.branchSelectedId == null || this.branchSelectedId == 0) {
                return {
                    id: 0,
                    address: '<none>'
                }
            } else {
                return this.branchList.find(item => item.id == this.branchSelectedId);

            }
        }
    },
    methods: {
        handleCommandBranch(command) {
            this.$emit('branchSelected', command);
        },
        async getBranches() {
            await axios.get(this.$store.state.baseUrl + "/admin/v1/branches?userId=" + localStorage.getItem('id'),
                { headers: { Authorization: 'Bearer ' + localStorage.getItem('user') } }
            )
                .then(res => {
                    this.branchList = res.data.data;
                })
                .catch(err => {
                    console.log(err)
                    this.$message.error('Lỗi hệ thống')
                })
        }
    },
    async mounted() {
        await this.getBranches();
    }
};

</script>

<style>
.branch-filter {
    display: flex;
    align-items: center;
}

.branch-filter .el-dropdown {
    width: 300px;
    border: 1px solid #DCDFE6;
    border-radius: 7px;
    height: 30px;
    background: white;
}

.branch-filter .title {
    color: rgb(41, 41, 41);
    font-size: 14px;
    margin-right: 5px;
}

.branch-filter .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 100%;
    margin: 0 10px 0 10px;
    color: rgb(41, 41, 41);
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
</style>