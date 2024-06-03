<template>
    <div class="product-details">
        <i class="el-icon-arrow-left" @click="$route.push('/')"></i>
        <div class="product-image">
            <img :src="product.img" alt="Product Image">
        </div>
        <div class="product-info">
            <label>{{ product.name }}</label>
            <span>{{ product.sumary }}</span>
        </div>
        <div class="quantitySold">
            <span>{{ product.quantitySold + " đã bán" }}</span>
        </div>
        <div class="size-price">
            <div>
                <span class="price">{{ curPrice }}</span>
                <el-dropdown trigger="click" @command="handleCommand">
                    <span class="el-dropdown-link">
                        {{ size }}
                        <i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item v-for="price in product.price" :key="price.size" :command='price.size'>
                            {{ price.size }}
                        </el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
            <i class="el-icon-plus" @click="showModal = true"></i>
        </div>
        <hr style="width: 100%;opacity: 40%;">
        <div class="description">
            <label>Mô tả</label>
            <span>{{ product.description }}</span>
        </div>
        <AddProductPopup :isPopupVisible="showModal" 
        @close="showModal = false"/>
    </div>
</template>

<script>
import AddProductPopup from './AddProductPopup.vue';

export default {
    components: {
        AddProductPopup,
    },
    data() {
        return {
            showModal: false,
            size: "S",
            product: {
                id: 123,
                img: "https://static.vinwonders.com/production/ca-phe-cot-dua-hai-phong-banner.jpeg",
                name: "Cà phê cốt dừa",
                sumary: "Cà phê cốt dừa ngon tuyệt",
                quantitySold: 12,
                description: "Một ly cafe cốt dừa là một hòa quyện tinh tế giữa hương vị đậm đà của cafe và sự ngọt ngào, béo ngậy của cốt dừa tươi mới. Khi bạn đưa ly cafe này lên mũi, hương thơm của cafe rang chín kết hợp với mùi ngào ngạt của cốt dừa tự nhiên tỏa ra, làm cho mọi cơn khát tỉnh dậy và mọi cơn mệt mỏi tan biến.",
                price: [
                    {
                        size: "S",
                        value: "35,000 đ",
                    },
                    {
                        size: "M",
                        value: "45,000 đ",
                    },
                    {
                        size: "L",
                        value: "55,000 đ",
                    },
                ],
            },
            quantity: 1
        }
    },
    computed: {
        curPrice() {
            const result = this.product.price.find(p => p.size === this.size);
            return result.value;
        },
    },
    methods: {
        handleCommand(command) {
            this.size = command;
        }

    }
}
</script>

<style>
@import '../../mixin/addProductPopup.css';
.product-details .el-icon-arrow-left {
    display: fixed;
    position: absolute;
    width: 35px;
    height: 35px;
    background-color: #00000057;
    top: 25px;
    left: 25px;
    border-radius: 7px;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #fff;
    font-size: 18px;
    font-weight: 800;
}

.product-details {
    display: flex;
    flex-direction: column;
    padding-top: 15px;
    overflow-y: auto;
    height: calc(100vh - 60px);
}

.product-image {
    width: 100%;
}

.product-image img {
    width: 100%;
    border-radius: 12px;
}

.product-info {
    width: 100%;
    display: flex;
    flex-direction: column;
    /* justify-content: space-between; */
    /* align-items: center; */
    margin: 10px 0 15px 0;
}

.product-info label {
    width: 70%;
    font-size: 24px;
    color: #451717;
    font-weight: bold;
    text-align: left;
}

.product-info span {
    margin-top: 10px;
    text-align: left;
    font-size: 15px;
    color: #333333;
    font-weight: 100;

}

.quantitySold {
    text-align: left;
    color: #858585;
    font-size: 14px;
    font-weight: 300;
    margin-top: 10px;
}

.size-price {
    width: 100%;
    display: flex;
    align-items: center;
    margin: 25px 0 15px;
    justify-content: space-between;
}

.size-price .price {
    width: 80px;
    font-size: 18px;
    font-weight: 500;
    text-align: left;
    color: #FF902A;
    margin-right: 10px;
}

.size-price .el-icon-plus {
    width: 24px;
    height: 24px;
    border-radius: 3px;
    font-size: 16px;
    font-weight: 700;
    color: #fff;
    margin-left: 10px;
    background-color: #FF902A;
    display: flex;
    justify-content: center;
    align-items: center;
}

.el-dropdown {
    width: 55px;
    font-size: 18px;
    font-weight: 500;
}

.el-dropdown-menu__item {
    font-family: "sarabun", "sans-serif";
    font-size: 16px;
}

.description {
    display: flex;
    flex-direction: column;
    align-items: start;
    margin-top: 25px
}

.description label {
    font-size: 18px;
    color: #414141;
    font-weight: 500;
    margin-bottom: 10px;
}

.description span {
    margin-top: 10px;
    text-align: justify;
    font-size: 15px;
    color: #7c7c7c;
    font-weight: 200;
}
</style>