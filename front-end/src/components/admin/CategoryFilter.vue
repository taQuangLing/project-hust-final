<template>
    <div class="category-filter">
        <span class="title">Loại</span>
        <el-dropdown @command="handleCommand">
            <span class="el-dropdown-link">
                {{ categorySelected.name }}<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item :command="item" v-for="item in categories" :key="item.id">{{
            item.name }}</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    props: ['categorySelectedId'],
    data(){
        return {
            categories: [],
        }
    },
    computed: {
        categorySelected() {
            if (this.categorySelectedId == null || this.categorySelectedId == 0) {
                return {
                    id: 0,
                    name: '<none>'
                }
            } else {
                return this.categories.find(item => item.id == this.categorySelectedId);

            }
        }
    },
    methods: {
        handleCommand(command) {
            this.$emit('categorySelected', command);
        },
        async getCategories() {
            await axios.get(this.$store.state.baseUrl + "/admin/v1/categories?userId=" + localStorage.getItem('id'),
                { headers: { Authorization: 'Bearer ' + localStorage.getItem('user') } }
            )
                .then(res => {
                    this.categories = res.data.data;
                })
                .catch(err => {
                    console.log(err)
                    this.$message.error('Lỗi hệ thống')
                })
        }
    },
    async mounted() {
        await this.getCategories();
    }
}

</script>

<style>
.category-filter {
    display: flex;
    align-items: center;
}

.category-filter .el-dropdown {
    width: 150px;
    border: 1px solid #DCDFE6;
    border-radius: 7px;
    font-size: 15px;
    height: 30px;
    background: white;
}

.category-filter .title {
    color: #575757;
    margin-right: 5px;
}

.category-filter .el-dropdown-link {
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