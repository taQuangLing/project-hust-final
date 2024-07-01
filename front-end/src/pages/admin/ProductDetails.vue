<template>
    <div class="product-details">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ name: 'products' }">Sản phẩm</el-breadcrumb-item>
            <el-breadcrumb-item>{{ product.name }}</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="action">
            <button class="save" @click="save">Lưu</button>
            <button class="close" @click="cancel">Hủy</button>
        </div>
        <div class="main">
            <div class="info">
                <div class="name">
                    <span>Tên đồ uống</span>
                    <el-input v-model="name"></el-input>
                </div>
                <CategoryFilter :categorySelectedId="categoryId" @categorySelected="selectCategory" />
                <div class="description">
                    <span>Mô tả</span>
                    <el-input type="textarea" v-model="description"></el-input>
                </div>
                <div class="img">
                    <span class="title">Ảnh</span>
                    <el-upload class="upload-demo" list-type="picture" :auto-upload="false" :limit="1" ref="upload"
                        :on-error="onError" :on-success="onSuccess" :data="data" :file-list="fileList"
                        action="https://api.cloudinary.com/v1_1/dn1pbep3e/upload">
                        <el-button size="small" type="primary">Click to upload</el-button>
                        <div slot="tip" class="el-upload__tip">jpg/png files with a size less than 500kb</div>
                    </el-upload>
                </div>
                <div class="status">
                    <span>Trạng thái</span>
                    <div class="status-info">
                        <el-input v-model="product.statusDis" :disabled="true"></el-input>
                        <el-switch v-model="status"></el-switch>
                    </div>
                </div>
                <div class="created-at">
                    <span>Ngày tạo</span>
                    <el-input :disabled="true" v-model="product.createdAt"></el-input>
                </div>
            </div>
            <div class="menu">
                <div class="header">
                    <span>SIZE</span>
                </div>
                <div class="wrapper">
                    <div class="no-size">
                        <el-radio v-model="hasSize" :label="0">Không size</el-radio>
                        <el-input v-model="price" :disabled="hasSize == 1"></el-input>
                    </div>
                    <div class="has-size">
                        <el-radio v-model="hasSize" :label="1">Có size</el-radio>
                        <el-button class="add-size" @click="addSize">Thêm</el-button>
                        <div class="check-box" v-if="hasSize">
                            <div class="check-item">
                                <span class="size">Size</span>
                                <span class="price" style="display: block; padding-right: 20px;">Giá</span>
                            </div>
                            <div class="check-item" v-for="item in sizeList" :key="item.id">
                                <el-input class="size" v-model="item.size"></el-input>
                                <div class="price">
                                    <el-input class="price-size" v-model="item.price"></el-input>
                                    <i class="el-icon-close" @click="deleteSizeItem(item)"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import axios from 'axios';
import CategoryFilter from '@/components/CategoryFilter.vue';

export default {
    components: {
        CategoryFilter
    },
    data() {
        return {
            product: {},
            img: "",
            name: "",
            description: "",
            status: false,
            categoryId: 0,
            fileList: [],
            hasSize: 0,
            price: 0,
            checkAll: false,
            isIndeterminate: true,
            sizeList: [],
        }
    },
    computed: {
        data() {
            return {
                upload_preset: 'bhdixbmd',
                api_key: '231664969325537'
            }
        },
    },
    methods: {
        getProduct() {
            axios.get(this.$store.state.baseUrl + "/admin/v1/products/" + this.$route.params.id + "?userId=" + localStorage.getItem("id"), {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("user")
                }
            })
                .then(res => {
                    this.product = res.data.data;
                    this.name = this.product.name;
                    this.description = this.product.description;
                    this.status = this.product.status == 1;
                    this.categoryId = this.product.categoryId;
                    this.hasSize = this.product.hasSize;
                    this.sizeList = this.product.sizeResponseList;
                    this.price = this.product.price;
                    this.img = this.product.img;
                    this.fileList.push({
                        url: this.img
                    })
                })
                .catch(err => {
                    console.log(err)
                })
        },
        save() {
            if(this.name == '' || this.categoryId == 0 || this.$refs.upload.uploadFiles.length == 0){
                this.$message.warning("Vui lòng nhập đầy đủ thông tin");
                return;
            }
            if ((this.hasSize == 1 && this.sizeList.length == 0) ||
                (this.hasSize == 0 && this.price == "")){
                this.$message.warning("Vui lòng nhập size");
                return;
            }

            if (this.$refs.upload.uploadFiles[0].status == "ready")this.$refs.upload.submit();
            else{
                this.updateProduct();
            }
            
        },
        updateProduct(){
            if (this.sizeList.length > 0){
                this.sizeList[0].isDefault = 1;
                for (let i = 1; i < this.sizeList.length; i++){
                    this.sizeList[i].isDefault = 0;
                }
            }

            this.status = this.status ? 1 : 0;

            axios.put(this.$store.state.baseUrl + "/admin/v1/products/" + this.product.id + "?userId=" + localStorage.getItem("id"), {
                name: this.name,
                categoryId: this.categoryId,
                description: this.description,
                img: this.img,
                status: this.status ? 1 : 0,
                hasSize: this.hasSize,
                price: this.price,
                sizeRequestList: this.sizeList,
                userId: localStorage.getItem("id")
            },
                {
                    headers: { Authorization: "Bearer " + localStorage.getItem("user") }
                },
            ).then(res => {
                if (res.data.code != 2000){
                    this.$message.error(res.data.message);
                    return;
                }
                this.$message.success("Cập nhật thành công");
                this.$router.push({ name: 'products' });
            }).catch(err => {
                console.log(err);
            });
        },
        cancel() {
            this.$router.push({ name: 'products' });
        },
        selectCategory(e) {
            this.categoryId = e.id;
        },
        handleChange(file) {
            this.file = file;
        },
        onError() {
            this.$message.error("Lỗi khi upload ảnh");
        },
        onSuccess(e) {
            this.img = e.url;
            this.updateProduct();
        },
        addSize() {
            if (this.sizeList.filter(item => item.size == "").length == 0)
                this.sizeList.push({
                    size: "",
                    price: ""
                })
        },
        deleteSizeItem(item){
            this.sizeList = this.sizeList.filter(i => i != item)
        }
    },
    mounted() {
        this.getProduct()
    }
} 
</script>

<style scoped>
.el-input>>>input {
    height: 30px;
    font-size: 15px;
    font-family: "sarabun";
}

.product-details {
    width: 100%;
    height: 100%;
    display: grid;
    grid-template-rows: 35px 45px;
}

.el-breadcrumb {
    height: 30px;
    padding-left: 40px;
    display: flex;
    align-items: center;
    font-size: 14px;
}

.action {
    width: 100%;
    display: flex;
    margin-top: 10px;
}

.close,
.save {
    height: 30px;
    width: 100px;
    background-color: white;
    border: 1px solid #797979;
    border-radius: 5px;
    font-size: 14px;
    margin-left: 40px;
}

.close:hover,
.save:hover {
    background-color: #f5f5f5;
}

.main {
    display: flex;
    width: 100%;
    height: 100%;
}

.info {
    width: 60%;
    border-right: 1px solid #949494;
    display: flex;
    flex-direction: column;
    align-items: start;
    padding: 30px 40px 0 40px;
}

.name,
.description,
.img,
.category-filter,
.status,
.created-at,
.status {
    display: flex;
    justify-content: space-between;
    width: 100%;
    margin-bottom: 25px;
}

.name span,
.description span,
.img .title,
.status span,
.created-at span,
.category-filter .title {
    text-align: left;
    font-size: 15px;
    color: #575757;
    display: flex;
    align-items: center;
}

.description span {
    padding-bottom: 120px;
}

.name .el-input,
.description .el-textarea,
.img .upload-demo,
>>>.category-filter .el-dropdown,
.created-at .el-input,
.status .status-info {
    width: 73%;
    border-radius: 5px;
}

.status-info {
    display: flex;
    width: 68%;
    align-items: center;
}

.status-info .el-input {
    width: 30%;
    margin-right: 30px;
}

.el-textarea>>>textarea {
    font-family: 'sarabun';
    font-size: 15px;
    height: 150px;
}

.upload-demo {
    text-align: left;
}

.menu {
    height: 100%;
    width: 40%;
}

.wrapper {
    background-color: white;
    display: flex;
    flex-direction: column;
    align-items: start;
    padding: 30px;
    margin: 15px 40px 10px 40px;
    gap: 20px;
    overflow-y: auto;
    /* border-top: 1px solid #949494; */
    /* border-bottom: 1px solid #949494; */
    box-shadow: 0px 5px 5px -3px rgba(0, 0, 0, 0.2), 0px 8px 10px 1px rgba(0, 0, 0, 0.14), 0px 3px 14px 2px rgba(0, 0, 0, 0.12);
    border-radius: 20px;
}

.no-size {
    display: flex;
    flex-direction: row;
    align-items: center;
    margin-bottom: 20px;
}

.has-size,
.check-box {
    display: flex;
    flex-direction: column;
    align-items: start;
    width: 100%;
}

.check-box {
    margin-top: 20px;
    width: 100%;
}

>>>.el-radio__label {
    font-size: 15px;
}

.check-item {
    width: 100%;
    display: flex;
    flex-direction: row;
    margin-top: 10px;
    align-items: center;
    justify-content: space-around;
}

.size {
    color: #545454;
    width: 70px;
}

.size>>>.el-input__inner {
    text-align: center;
}

.price {
    color: #545454;
    width: 200px;
    display: flex;
    flex-direction: row;
    align-items: center;
}

.price-size {
    width: 200px;
    margin-right: 15px;
}

.el-icon-close {
    cursor: pointer;
    font-size: 20px;
}

.add-size {
    margin-top: 40px;
    height: 35px;
    padding: 0 25px 0 25px;
}
</style>