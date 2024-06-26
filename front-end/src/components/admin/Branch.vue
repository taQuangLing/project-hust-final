<template>
    <div class="main">
        <div class="filter">
            <div class="btn-add">
                <button @click="showAddPopup = true">
                    <i class="el-icon-circle-plus-outline"></i>
                    <span>Thêm chi nhánh mới</span>
                </button>
            </div>
            <el-input placeholder="Tên chi nhánh..." v-model="branch">
                <i slot="prefix" class="el-input__icon el-icon-search"></i>
            </el-input>

            <StatusFilter
                :statusSelected="statusSelected"
                @status="selectStatus"
            />
            <div class="reset">
                <el-button icon="el-icon-refresh-left" @click="reset"
                    >Làm mới</el-button
                >
            </div>
        </div>
        <div class="pagination">
            <el-pagination
                :page-size="pageSize"
                layout="prev, pager, next"
                :total="length"
                @current-change="setPage"
            ></el-pagination>
            <span class="record-count"
                >{{ (page - 1) * pageSize + 1 }}-{{
                    Math.min(length, page * pageSize)
                }}/{{ length }} bản ghi</span
            >
        </div>
        <div class="table">
            <el-table
                :data="branchList"
                stripe
                style="width: 100%"
                ref="table"
                :cell-style="statusStyle"
            >
                <el-table-column prop="createdAt" label="Ngày tạo" width="170">
                </el-table-column>
                <el-table-column prop="branch" label="Chi nhánh" width="420">
                </el-table-column>
                <el-table-column prop="phoneNumber" label="Số điện thoại">
                </el-table-column>
                <el-table-column prop="manager" label="Quản lý">
                </el-table-column>
                <el-table-column prop="status" label="Trạng thái">
                </el-table-column>
                <el-table-column label="Hành động">
                    <template slot-scope="scope">
                        <el-dropdown
                            @command="handleCommandItem"
                            trigger="click"
                        >
                            <span class="el-dropdown-link">
                                Hành động<i
                                    class="el-icon-arrow-down el-icon--right"
                                ></i>
                            </span>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item
                                    :command="{
                                        action: 'edit',
                                        index: scope.$index,
                                        row: scope.row,
                                    }"
                                    >Chỉnh sửa</el-dropdown-item
                                >
                                <el-dropdown-item
                                    :command="{
                                        action: 'delete',
                                        index: scope.$index,
                                        row: scope.row,
                                    }"
                                    >Xóa</el-dropdown-item
                                >
                            </el-dropdown-menu>
                        </el-dropdown>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <AddEmployee
            v-if="showAddPopup"
            class="add-popup"
            @close="showAddPopup = false"
            @userCreated="createdUser()"
        />
        <AddBranch
            v-if="showAddPopup"
            class="add-popup"
            @close="showAddPopup = false"
            @branchCreated="createdBranch()"
        />
    </div>
</template>

<script>
import BranchFilter from './BranchSelect.vue';
import AddBranch from './AddBranchPopup.vue';
import StatusFilter from './StatusFilter.vue';
import axios from 'axios';

export default {
    components: {
        BranchFilter,
        AddBranch,
        StatusFilter,
    },
    data() {
        return {
            branchSelectedId: 0,
            createdAt: new Date(),
            phoneNumber: '0902112312321',
            statusSelected: '<none>',
            branch: 'Hello Ling',
            manager: 'Linh Ta',
            page: 1,
            pageSize: 20,
            length: 0,
            showAddPopup: false,
            showEditPopup: false,
            employeeId: 0,
            branchList: [
                {
                    createdAt: new Date(),
                    phoneNumber: '0902112312321',
                    statusSelected: '<none>',
                    branch: 'Hello Ling',
                    manager: 'Linh Ta',
                    status: 'Good',
                },
            ],
        };
    },
};
</script>

<style scoped>
@import '../../mixin/pagination.css';
@import '../../mixin/btn-add.css';
@import '../../mixin/refresh-btn.css';

.add-popup {
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(39, 39, 39, 0.863);
    display: flex;
    flex-direction: column;
    justify-content: end;
}

.main >>> .add-popup {
    .add-branch {
        width: 40%;
        height: 70%;
        min-width: 650px;
        min-height: 600px;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        background: rgb(255, 255, 255);
        box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
        display: flex;
        flex-direction: column;
        font-size: 15px;
        background-color: white;
    }

    h1 {
        /* font-family: 'sarabun'; */
        font-weight: 600;
        font-size: 28px;
        color: rgb(51, 51, 51);
        padding: 10px 0 10px;
        border-bottom: 1px solid rgb(177, 177, 177);
        margin: 0 20px 0 20px;
    }

    .info {
        display: flex;
        flex-direction: column;
        align-items: start;
        padding: 30px 120px 20px 120px;
        justify-content: space-between;
        gap: 20px;
    }

    .name,
    .address,
    .city,
    .district,
    .email,
    .branch-filter,
    .img,
    .position-filter {
        display: flex;
        justify-content: space-between;
        width: 100%;
    }

    .name span,
    .address span,
    .city span,
    .district span,
    .email span,
    .img span,
    .branch-filter .title,
    .position-filter .title {
        text-align: left;
        font-size: 15px;
        color: #575757;
        display: flex;
        align-items: center;
    }

    .name .el-input,
    .address .el-input,
    .city .el-input,
    .district .el-input,
    .email .el-input,
    .branch-filter .el-dropdown,
    .position-filter .el-dropdown {
        width: 68%;
        border-radius: 5px;
    }

    .name input,
    .phone input,
    .email input {
        border: 1px solid #dcdfe6;
        font-size: 15px;
    }

    .action {
        width: 100%;
        display: flex;
        justify-content: space-evenly;
        margin-top: 20px;
        position: absolute;
        bottom: 30px;
        height: auto;
    }

    .close,
    .save {
        height: 30px;
        width: 100px;
        background-color: white;
        border: 1px solid #797979;
        border-radius: 5px;
        font-size: 14px;
    }

    .close:hover,
    .save:hover {
        background-color: #f5f5f5;
    }
}

.filter {
    display: flex;
    padding: 10px 0 10px 0;
    align-items: center;
    height: 35px;
    padding-left: 20px;
}

.el-dropdown-link {
    cursor: pointer;
    color: #409eff;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 100%;
    margin: 0 10px 0 10px;
    color: rgb(41, 41, 41);
}

.el-icon-arrow-down {
    font-size: 12px;
}

.el-input {
    width: 200px;
    margin-right: 20px;
    border: #000000;
}

>>> .el-input__inner {
    height: 30px;
    border-radius: 5px;
    font-size: 14px;
}

.el-input__icon {
    display: flex;
    align-items: center;
    margin-left: 5px;
}

.phone-number {
    width: 150px;
}

.table {
    display: flex;
    flex-direction: column;
    overflow-y: auto;
    height: 100%;
    margin: 0 20px 0 20px;
}
.el-table {
    height: 100%;
    overflow-y: auto;
}

.main {
    width: 100%;
    height: 100%;
    display: grid;
    grid-template-rows: 55px 33px;
}

>>> .cell {
    text-align: center;
}

.el-table::before {
    width: 0;
    height: 0;
}

.content .el-dropdown {
    border: 1px solid #7a7a7a;
    height: 30px;
    border-radius: 7px;
}

.el-dropdown-menu {
    border-radius: 2px;
    border: 1px solid #7a7a7a;
}

.img .upload-demo {
    width: 73%;
    border-radius: 5px;
}

textarea {
    font-family: 'sarabun';
    font-size: 15px;
    height: 80px;
}

.upload-demo {
    text-align: left;
}
</style>
