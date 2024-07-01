<template>
    <div @click.self="close">
        <div class="category-popup">
            <h1>THÊM PHÂN LOẠI MỚI</h1>
            <div class="info">
                <div class="name">
                    <span>Tên phân loại</span>
                    <el-input v-model="name"></el-input>
                </div>
                <div class="description">
                    <span>Mô tả</span>
                    <el-input type="textarea" v-model="description"></el-input>
                </div>
                <div class="img">
                    <span class="title">Ảnh</span>
                    <el-upload class="upload-demo" list-type="picture" :auto-upload="false" :limit="1" ref="upload"
                        :on-change="handleChange" :on-error="onError" :on-success="onSuccess" :data="data"
                        action="https://api.cloudinary.com/v1_1/dn1pbep3e/upload">
                        <el-button size="small" type="primary">Click to upload</el-button>
                        <div slot="tip" class="el-upload__tip">jpg/png files with a size less than 500kb</div>
                    </el-upload>
                </div>
            </div>
            <div class="action">
                <button class="close" @click="close">Đóng</button>
                <button class="save" @click="save">Lưu</button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";

export default {
    data() {
        return {
            name: "",
            description: "",
            img: "",
            file: '',
        }
    },
    computed: {
        data() {
            return {
                upload_preset: 'bhdixbmd',
                api_key: '231664969325537'
            }
        }
    },
    methods: {
        close() {
            this.$emit('close');
        },
        save() {
            if (this.name == ""){
                this.$message.warning("Vui lòng nhập đầy đủ thông tin");
                return false;
            }
            this.$refs.upload.submit();
        },
        handleChange(file) {
            this.file = file;
        },
        onError() {
            this.$message.error("Lỗi khi upload ảnh");
        },
        onSuccess(e) {
            this.img = e.url;
            axios.post(this.$store.state.baseUrl + "/admin/v1/categories?userId=" + localStorage.getItem("id"), {
                name: this.name,
                description: this.description,
                img: this.img,
                userId: localStorage.getItem("id")
            },
                {
                    headers: { Authorization: "Bearer " + localStorage.getItem("user") }
                },
            ).then(res => {
                this.$emit('categoryCreated');
            }).catch(err => {
                console.log(err);
            });
        },
        selectCategory(e) {
            this.categoryId = e.id;
        }
    }
}
</script>