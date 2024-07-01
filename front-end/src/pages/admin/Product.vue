<template>
    <div class="main">
        <div class="filter">
            <div class="btn-add add-product">
                <button @click="showAddProductPopup = true">
                    <i class="el-icon-circle-plus-outline"></i>
                    <span>Thêm mới</span>
                </button>
            </div>
            <div class="btn-add add-category">
                <button @click="showAddCategoryPopup = true">
                    <i class="el-icon-circle-plus-outline"></i>
                    <span>Thêm mới loại</span>
                </button>
            </div>
            <el-input placeholder="Tên sản phẩm..." v-model="productName">
                <i slot="prefix" class="el-input__icon el-icon-search"></i>
            </el-input>
            <StatusFilter :statusSelected="statusSelected" @status="selectStatus" />
            <CategoryFilter :categorySelectedId="categorySelectedId" @categorySelected="selectCategory" />
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
            <el-table :data="productsResult" stripe style="width: 100%" ref="table" :cell-style='statusStyle'
                :header-cell-style='headerStyle'>
                <el-table-column prop="id" label="ID" width="100">
                </el-table-column>
                <el-table-column width="150">
                    <template slot-scope="scope">
                        <img :src="scope.row.img" alt="" style="width: 80px; height: 60px">
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="Tên sản phẩm" width="300">
                </el-table-column>
                <el-table-column prop="categoryName" label="Loại">
                </el-table-column>
                <el-table-column prop="priceDisplay" label="Giá">
                </el-table-column>
                <el-table-column prop="status" label="Trạng thái">
                </el-table-column>
                <el-table-column label="Hành động" width="150">
                    <template slot-scope="scope">
                        <el-dropdown @command="handleCommandItem" trigger="click">
                            <span class="el-dropdown-link">
                                Hành động<i class="el-icon-arrow-down el-icon--right"></i>
                            </span>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item
                                    :command='{ action: "edit", index: scope.$index, row: scope.row }'>Chỉnh
                                    sửa</el-dropdown-item>
                                <el-dropdown-item
                                    :command='{ action: "delete", index: scope.$index, row: scope.row }'>Xóa</el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <AddProductPopup v-if="showAddProductPopup" class="add-product-popup" @close="showAddProductPopup = false"
            @productCreated="createProduct" />
        <AddCategoryPopup v-if="showAddCategoryPopup" class="add-category-popup" @close="showAddCategoryPopup = false"
            @categoryCreated="createCategory" />
    </div>
</template>

<script>
import StatusFilter from "@/components/StatusFilter.vue"
import CategoryFilter from "@/components/CategoryFilter.vue"
import AddProductPopup from "./AdminAddProductPopup.vue"
import AddCategoryPopup from "./AddCategoryPopup.vue"
import axios from "axios";

export default {
    components: {
        StatusFilter,
        CategoryFilter,
        AddProductPopup,
        AddCategoryPopup
    },
    data() {
        return {
            showAddProductPopup: false,
            showAddCategoryPopup: false,
            categorySelectedId: 0,
            productName: '',
            statusSelected: "<none>",
            products: [],
            page: 1,
            pageSize: 20,
            length: 0,
        }
    },
    computed: {
        productsResult() {
            let productsResult = this.products.filter((product) => {
                return product.name.toLowerCase().includes(this.productName.toLowerCase()) &&
                    (this.statusSelected === "<none>" || product.status === this.statusSelected) &&
                    (this.categorySelectedId === 0 || product.categoryId === this.categorySelectedId);
            });
            this.length = productsResult.length;
            return productsResult.slice((this.page - 1) * this.pageSize, this.page * this.pageSize);
        }
    },
    methods: {
        getProducts() {
            axios.get(this.$store.state.baseUrl + "/admin/v1/products?userId=" + localStorage.getItem("id"),
                {
                    headers: { Authorization: "Bearer " + localStorage.getItem("user") }
                }).then((response) => {
                    this.products = response.data.data;
                }).catch((error) => {
                    console.log(error);
                });
        },
        selectCategory(e) {
            this.categorySelectedId = e.id;
            this.showAddProductPopup = false;
        },
        selectStatus(e) {
            this.statusSelected = e;
        },
        statusStyle(cell) {
            let style = {
                textAlign: "center"
            };
            if (cell.columnIndex == 2) {
                style.textAlign = "left";
                style.fontWeight = "600";
            }
            if (cell.columnIndex == 5) {
                switch (cell.row.status) {
                    case "Hoạt động":
                        style.color = "#00d306";
                        break;
                    case "Không hoạt động":
                        style.color = "#bda014";
                        break;
                    default:
                }
            }
            return style;
        },
        headerStyle(cell) {
            let style = {
                textAlign: "center"
            };
            if (cell.columnIndex == 2) {
                style.textAlign = "left";
            }
            return style;
        },
        reset() {
            this.productName = '';
            this.statusSelected = "<none>";
            this.categorySelectedId = 0;
        },
        handleCommandItem(command) {
            switch (command.action) {
                case "edit":
                    this.$router.push({ name: "AdminProductDetails", params: { id: command.row.id } });
                    break;
                case "delete":
                    this.deleteProduct(command.row.id);
                    break;
            }
        },
        setPage(val) {
            this.page = val
        },
        deleteProduct(id) {
            this.$confirm('Bạn có chắc chắn muốn xóa?', 'Warning', {
                confirmButtonText: 'OK',
                cancelButtonText: 'Cancel',
                type: 'warning',
                title: 'Confirm'
            }).then(() => {
                axios.delete(this.$store.state.baseUrl + "/admin/v1/products/" + id + "?userId=" + localStorage.getItem("id"),
                {
                    headers: { Authorization: "Bearer " + localStorage.getItem("user") }
                }).then((response) => {
                    if (response.data.code != 2000 || response.data.code == 4012) {
                        this.$message.error(response.data.description);
                        return;
                    }
                    this.$message.success("Xóa sản phẩm thành công");
                    this.getProducts();
                }).catch((error) => {
                    console.log(error);
                })

            })
        },
        createProduct() {
            this.getProducts();
            this.showAddProductPopup = false;
        },
        createCategory() {
            this.showAddCategoryPopup = false;
        }
    },
    mounted() {
        this.getProducts();
    }
}

</script>

<style scoped>
@import "../../mixin/btn-add.css";
@import "../../mixin/pagination.css";
@import "../../mixin/refresh-btn.css";

.add-product-popup,
.add-category-popup {
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

.main>>>.add-product-popup {
    .product-popup {
        width: 40%;
        height: 70%;
        min-width: 650px;
        min-height: 620px;
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
    }

    .name,
    .description,
    .category-filter,
    .img {
        display: flex;
        justify-content: space-between;
        width: 100%;
        margin-bottom: 18px;
    }

    .name span,
    .description span,
    .category-filter span,
    .checkbox span,
    .img .title {
        text-align: left;
        font-size: 15px;
        color: #575757;
        display: flex;
        align-items: center;
    }

    .description span {
        padding-bottom: 100px;
    }

    .name .el-input,
    .description .el-textarea,
    .category-filter .el-dropdown,
    .img .upload-demo {
        width: 73%;
        border-radius: 5px;
    }

    textarea {
        font-family: 'sarabun';
        font-size: 15px;
        height: 120px;
    }

    .upload-demo {
        text-align: left;
    }

    .checkbox {
        display: flex;
        align-items: center;
    }

    .checkbox span {
        margin-right: 20px;
    }

    .checkbox .title {
        width: 95px;
        margin-right: 50px;
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

.main>>>.add-category-popup {
    .category-popup {
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
        height: 230px;
    }

    .name,
    .description,
    .img {
        display: flex;
        justify-content: space-between;
        width: 100%;
        margin-bottom: 20px;
    }

    .name span,
    .description span,
    .img .title {
        text-align: left;
        font-size: 15px;
        color: #575757;
        display: flex;
        align-items: center;
    }

    .description span {
        padding-bottom: 100px;
    }

    .name .el-input,
    .description .el-textarea,
    .img .upload-demo {
        width: 73%;
        border-radius: 5px;
    }

    textarea {
        font-family: 'sarabun';
        font-size: 15px;
        height: 120px;
    }

    .upload-demo {
        text-align: left;
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


.status-filter {
    margin-right: 30px;
}

.filter {
    height: 35px;
    padding: 10px 0 10px 20px;
    display: flex;
    flex-direction: row;
    align-items: center;
}

.add-product {
    margin-right: 20px;
}

.add-product button {
    width: 110px;
}

.add-category {
    margin-right: 20px;
}

.add-category button {
    width: 140px;
}

.el-input {
    width: 300px;
    margin-right: 10px;
    border: #000000;
}

>>>.el-input__inner {
    height: 30px;
    border-radius: 5px;
    font-size: 14px;
}

.el-input__icon {
    height: 30px;
    display: flex;
    align-items: center;
    margin-left: 5px;
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

.content {
    display: flex;
    flex-direction: column;
    overflow-y: auto;
    height: 100%;
    margin: 0 20px 0 20px;
}

.el-table::before {
    width: 0;
    height: 0;
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

.table .el-dropdown {
    border: 1px solid #7a7a7a;
    height: 30px;
    border-radius: 7px;
}

.el-dropdown-menu {
    border-radius: 2px;
    border: 1px solid #7a7a7a;
}
</style>