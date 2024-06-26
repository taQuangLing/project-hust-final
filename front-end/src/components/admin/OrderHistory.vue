<template>
    <div class="main">
        <div class="filter">
            <BranchFilter @branchSelected="selectBranch" class="branch-filter" :branchSelectedId="branchSelectedId">
            </BranchFilter>
            <div class="status">
                <span>Trạng thái</span>
                <el-dropdown @command="handleCommandStatus">
                    <span class="el-dropdown-link">
                        {{ statusSelected }}<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <!-- <el-dropdown-item command="-1" >{{ "<none>" }}</el-dropdown-item> -->
                        <el-dropdown-item command="0">{{ "Chờ xác nhận" }}</el-dropdown-item>
                        <el-dropdown-item command="1">{{ "Đang pha chế" }}</el-dropdown-item>
                        <el-dropdown-item command="2">{{ "Hoàn thành" }}</el-dropdown-item>
                        <el-dropdown-item command="3">{{ "Đã hủy" }}</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
            <div class="date-filter">
                <div class="block">
                    <span class="demonstration">Ngày</span>
                    <el-date-picker v-model="value1" type="daterange" range-separator="To"
                        start-placeholder="Start date" end-placeholder="End date">
                    </el-date-picker>
                </div>
            </div>
            <div class="reset">
                <el-button icon="el-icon-refresh-left" @click="reset">Làm mới</el-button>
            </div>
        </div>
        <div class="pagination">
            <el-pagination :page-size="pageSize" layout="prev, pager, next" :total="length"
                @current-change="setPage"></el-pagination>
            <span class="record-count">{{ (page - 1) * pageSize + 1 }}-{{ Math.min(length, (page) * pageSize) }}/{{
                length }} bản ghi</span>
        </div>
        <div class="table">
            <el-table :data="ordersResult" stripe style="width: 100%" ref="table" :cell-style='statusStyle'>
                <el-table-column prop="datetime" label="Thời gian" width="200">
                </el-table-column>
                <el-table-column prop="branch" label="Chi nhánh" width="450">
                </el-table-column>
                <el-table-column prop="payments" label="Phương thức thanh toán">
                </el-table-column>
                <el-table-column prop="status" label="Trạng thái">
                </el-table-column>
                <el-table-column prop="total" label="Tổng tiền">
                </el-table-column>
                <el-table-column label="Hành động">
                    <template slot-scope="scope">
                        <el-dropdown @command="handleCommandItem" trigger="click">
                            <span class="el-dropdown-link">
                                Hành động<i class="el-icon-arrow-down el-icon--right"></i>
                            </span>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item
                                    :command='{ action: "detail", index: scope.$index, row: scope.row }'>Chi
                                    tiết</el-dropdown-item>
                                <el-dropdown-item
                                    :command='{ action: "delete", index: scope.$index, row: scope.row }'>Xóa</el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <OrderDetails v-if="showDetails" :orderId="orderId" @close="showDetails = false" class="popup" />
    </div>
</template>

<script>
import axios from 'axios';
import BranchFilter from './BranchSelect.vue'
import { convertStringToDate } from '@/helper/utils.js';
import OrderDetails from './OrderDetails.vue';

export default {
    components: {
        BranchFilter,
        OrderDetails
    },
    data() {
        return {
            branchSelectedId: 0,
            statusSelected: "<none>",
            value1: '',
            orders: [],
            page: 1,
            pageSize: 20,
            length: 0,
            showDetails: false,
        }
    },
    computed: {
        ordersResult() {
            let ordersResult = this.orders.filter(order => {
                const orderAt = new Date(convertStringToDate(order.datetime));
                orderAt.setHours(0, 0, 0, 0);
                return (this.branchSelectedId == 0 || order.branchId == this.branchSelectedId) &&
                    (this.statusSelected === "<none>" || order.status === this.statusSelected) &&
                    (this.value1 === '' || (orderAt >= this.value1[0] && orderAt <= this.value1[1]));
            });
            this.length = ordersResult.length;

            return ordersResult.slice((this.page - 1) * this.pageSize, this.page * this.pageSize);
        }
    },
    methods: {
        handleCommandStatus(command) {
            switch (command) {
                case "0":
                    this.statusSelected = "Chờ xác nhận";
                    break;
                case "1":
                    this.statusSelected = "Đang pha chế";
                    break;
                case "2":
                    this.statusSelected = "Hoàn thành";
                    break;
                case "3":
                    this.statusSelected = "Đã hủy";
                    break;
            }
        },
        handleCommandItem(command) {
            switch (command.action) {
                case "detail":
                    this.showDetails = true;
                    this.orderId = command.row.id;
                    break;
                case "delete":
                    this.deleteOrder(command.row.id);
                    break;
            }
        },
        async deleteOrder(id) {
            this.$confirm('Bạn có chắc chắn muốn xóa?', 'Warning', {
                confirmButtonText: 'OK',
                cancelButtonText: 'Cancel',
                type: 'warning',
                title: 'Confirm'
            }).then(() => {
                axios.delete(this.$store.state.baseUrl + "/admin/v1/orders/" + id + "?userId=" + localStorage.getItem("id"),
                    {
                        headers: { Authorization: "Bearer " + localStorage.getItem("user") }
                    }
                ).then(res => {
                    this.$message({
                        type: 'success',
                        message: 'Xóa thành công'
                    });
                    this.getOrders();
                })
                    .catch(err => {
                        console.log(err);
                    });
            }).catch(() => {
            });

        },
        async getOrders() {
            await axios.get(this.$store.state.baseUrl + "/admin/v1/orders?userId=" + localStorage.getItem("id"),
                {
                    headers: { Authorization: "Bearer " + localStorage.getItem("user") }
                }
            ).then(res => {
                this.orders = res.data.data;
            })
                .catch(err => {
                    console.log(err);
                });
        },
        setPage(val) {
            this.page = val
        },
        reset() {
            this.branchSelectedId = 0;
            this.statusSelected = "<none>";
            this.value1 = '';
            this.value2 = '';
        },
        selectBranch(branch) {
            this.branchSelectedId = branch.id;
        },
        statusStyle(cell) {
            if (cell.columnIndex === 3) {
                if (cell.row.status === "Chờ xác nhận") {
                    return { color: "#FFA500" };
                }
                if (cell.row.status === "Đang pha chế") {
                    return { color: "#3278CA" };
                }
                if (cell.row.status === "Hoàn thành") {
                    return { color: "#0D9F1C" };
                }
                if (cell.row.status === "Đã hủy") {
                    return { color: "#CF2127" };
                }
            }
        }
    },
    async beforeMount() {
        await this.getOrders();
    }
}
</script>

<style scoped>
@import '../../mixin/pagination.css';
.popup {
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

.main>>>.popup {
    .order-details {
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
        justify-content: space-between;
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
        padding: 30px 50px 20px 50px;
        justify-content: space-between;
        height: 150px;
    }

    .datetime label,
    .branch label,
    .payments label,
    .status label,
    .note label {
        width: 200px;
        color: rgb(117, 117, 117);
        display: inline-flex;
        font-weight: 200;
    }

    .bill {
        padding: 0 50px 20px 50px;
        margin-top: 25px;
    }

    h3 {
        text-align: left;
        font-weight: 300;
        font-size: 16px;
        margin: 0;
        color: #7c7c7c;
    }

    .wrapper {
        overflow-y: auto;
        height: 200px;
        padding-bottom: 10px;
        padding-right: 10px;
        margin-top: 15px;
        scrollbar-width: thin;
        scrollbar-color: #b4b4b4 #f5f5f5;
    }

    .wrapper::-webkit-scrollbar {
        width: 8px;
    }

    .wrapper::-webkit-scrollbar-track {
        background-color: #f5f5f5;
    }

    .wrapper::-webkit-scrollbar-thumb {
        background-color: #888888;
        border-radius: 4px;
    }

    .wrapper::-webkit-scrollbar-thumb:hover {
        background-color: #555555;
    }

    .order-item {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
    }

    .order-item span {
        margin-top: 5px;
        margin-bottom: 18px;
    }

    .index {
        margin-right: 5px;
    }

    .name-wrapper {
        width: 60%;
        text-align: left;
    }

    .name {
        text-align: left;
        margin-right: 40px;
        font-weight: 600;
    }

    .quantity {
        width: 50px;
    }

    .price {
        width: 100px;
        text-align: right;
    }

    .total {
        display: flex;
        justify-content: space-between;
        margin-top: 10px;
        padding-top: 7px;
        border-top: 1px solid rgb(161, 161, 161);
        font-size: 16px;
        font-weight: 500;
        padding-right: 10px;
    }

    .close {
        height: 30px;
        width: 100px;
        margin: 0 auto;
        margin-bottom: 20px;
        margin-top: 10px;
        background-color: white;
        border: 1px solid #797979;
        border-radius: 5px;
        font-size: 14px;
    }

    .close:hover {
        background-color: #f5f5f5;
    }
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


.el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
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

.branch-filter,
.status,
.date-filter,
.reset
{
    margin-left: 20px;
    margin-right: 10px;
    display: flex;
    align-items: center;
}

.block {
    display: flex;
    align-items: center;

}

.filter {
    display: flex;
    padding: 10px 0 10px 0;
    align-items: start;
}

.status .el-dropdown {
    width: 150px;
    border: 1px solid #7a7a7a;
    border-radius: 7px;
    height: 30px;
    background: white;
}

.status span,
.block span {
    color: rgb(41, 41, 41);
    font-size: 14px;
    margin-right: 5px;
}

.el-range-editor.el-input__inner {
    padding: 0 10px 0 10px;
    width: 300px;
    height: 35px;
    border-radius: 7px;
    border: 1px solid #7a7a7a;
}

.reset .el-button {
    font-size: 14px;
    padding: 0;
    font-family: 'sarabun';
    width: 100px;
    height: 35px;
    border-radius: 7px;
    border: 1px solid #7a7a7a;
    color: #444444;
}

.reset .el-button:focus {
    border: 1px solid #DCDFE6;
    color: #606266;
    outline: 0;
    background-color: white;
}

.table {
    display: flex;
    flex-direction: column;
    overflow-y: auto;
    height: 100%;
    margin: 0 20px 0 20px;
}

.action-item {
    border: 1px solid #7a7a7a;
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

>>>.cell {
    text-align: center;
}

.el-table::before {
    width: 0;
    height: 0;
}
</style>