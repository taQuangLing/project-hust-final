<template>
  <div class="home" v-loading="loading">
    <div class="tab-container">
      <div :class='{ "tab-item": true, "tab-item-active": activeTab === 0 }' @click="activeTab = 0">
        <i class="el-icon-menu" style="font-size: 50px; color: #acacac; margin-bottom: 3px"></i>
        <label>Tất cả</label>
      </div>
      <div v-for="(category, index) in categories" :key="category.id"
        :class='{ "tab-item": true, "tab-item-active": activeTab === index + 1 }' @click="activeTab = index + 1">
        <img :src="category.img" alt="category.name" />
        <label>{{ category.name }}</label>
      </div>
    </div>
    <div class="content-container">
      <div class="drink" v-for="(product, index) in products" :key="product.id">
        <router-link :to="`/products/${product.id}`">
          <div class="info">
            <img :src="product.img" alt="" />
            <div class="info-detail">
              <label>{{ product.name }}</label>
              <p>{{ product.sumary }}</p>
              <span>{{ product.priceDisplay }}</span>
            </div>
          </div>
        </router-link>
        <i class="el-icon-plus" @click="showPopup(product.id)"></i>
      </div>
      <AddProductPopup v-if="showModal" :productId="productId" @close="showModal = false" class="popup" />
    </div>
  </div>

</template>

<script>
import AddProductPopup from './GuestAddProductPopup.vue';
import axios from 'axios';
import { jwtDecode } from "jwt-decode";

export default {
  components: {
    AddProductPopup
  },
  data() {
    return {
      activeTab: 0,
      showModal: false,
      loading: true,
      categories: [],
      products: [],
      productId: 0,
    };
  },
  methods: {
    async getCategories() {
      const branchId = localStorage.getItem('branchId');
      const code = localStorage.getItem('code');

      await axios.get(this.$store.state.baseUrl + "/guest/v1/menu?" + "branchId=" + branchId + "&code=" + code,
        { headers: { Authorization: `Bearer ${localStorage.getItem('user')}` } }
      )
        .then((response) => {
          this.categories = response.data.data.categories;
        })
        .catch((error) => {
          console.log(error);
          this.categories = [];
        });
    },
    getProducts() {
      this.products = [];
      if (this.activeTab === 0) {
        this.prducts = this.categories.map(category => {
          category.products.map(product => {
            this.products.push(product);
          });
        });
      } else {
        this.products = this.categories[this.activeTab - 1].products;
      }
    },
    showPopup(productId) {
      this.showModal = true;
      this.productId = productId;
    },
  },
  beforeUpdate() {
    this.getProducts();
  },
  mounted() {
    this.getCategories();
    this.loading = false;
  },
};
</script>

<style scoped>
.content-container>>>.popup {
  .popup-overlay {
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

  .popup-content {
    /* height: 63%; */
    background-color: white;
    border-radius: 10px;
    display: flex;
    align-items: center;
    flex-direction: column;
    padding: 0 15px 0 15px;
    overflow-y: auto;
  }

  .header {
    width: 100%;
    height: 45px;
    /* background: gray; */
    display: flex;
    align-items: center;
    justify-content: space-evenly;
  }

  .header label {
    font-size: 18px;
    font-weight: 600;
    color: rgb(49, 49, 49);
  }

  .header .el-icon-close {
    display: fixed;
    position: absolute;
    right: 13px;
    top: 233px;
    font-size: 20px;
  }

  .product {
    display: flex;
    flex-direction: row;
    width: 100%;
    margin: 10px 0 10px;
  }

  .info img {
    width: 70px;
    height: 70px;
    border-radius: 5px;
    flex: 0 0 70px;
  }

  .info {
    display: flex;
    flex: 1;
    padding: 10px 0 10px;
    width: 100%;
  }

  .info-detail {
    text-align: left;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    flex: 1;
    padding: 0 15px 0 13px;
  }

  .info-detail label {
    color: #451717;
    font-size: 18px;
    font-weight: 700;
    margin-bottom: 3px;
  }

  .info-detail p {
    margin: 0;
    color: #696969;
    font-size: 12px;
    margin-bottom: 3px;
  }

  .info-detail span {
    color: #ff902a;
    font-size: 15px;
    font-weight: bold;
    width: 100%;
  }

  .action {
    padding-bottom: 10px;
    display: flex;
    align-items: end;
  }

  .action .icon {
    width: 15px;
    height: 15px;
    padding: 5px;
    font-size: 14px;
    border-radius: 3px;
  }

  .el-icon-minus, .el-icon-plus {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .action .el-icon-minus {
    border: solid 1px #FF902A;
    color: #FF902A;
    width: 9px;
    height: 9px;
  }

  .action .el-icon-plus {
    background: #FF902A;
    color: #FF902A;
    color: #fff;
    width: 11px;
    height: 11px;
  }

  .action span {
    padding: 0 8px 2px 8px;
    font-size: 15px;
  }

  .size {
    width: 100%;
    display: flex;
    flex-direction: column;
    margin-top: 15px;
    align-items: start;
  }

  .size-item {
    margin: 15px 0 35px 10px;
  }

  .size-item .el-radio__label {
    font-size: 16px;
    font-weight: 400;
  }

  .size-item .el-radio__inner {
    width: 16px;
    height: 16px;
  }

  hr {
    opacity: 40%;
    width: 100%;
  }

  .note {
    margin: 15px 0 50px;
    width: 100%;
  }

  .note .el-textarea__inner {
    height: 120px;
    font-size: 16px;
    font-weight: 300;
    font-family: "Sarabun", "Sans-serif";
  }

  .submit {
    height: 110px;
    width: 100%;
  }

  .submit .el-button {
    width: 100%;
    height: calc(100% - 60px);
    border-radius: 0px;
    background: #FF7A00;
    border: none;
    color: #fff;
    font-size: 17px;
    font-family: "Sarabun", "Sans-serif";
    font-weight: 300;
  }

  .submit .el-button--primary.is-plain:focus,
  .el-button--primary.is-plain:hover {
    background: #FF7A00;
    color: #fff;
  }
}

.home {
  height: 100%;
  display: grid;
  grid-template-rows: 110px 1fr;
}

.tab-container {
  display: flex;
  width: 100%;
  display: flex;
  overflow-x: auto;
  white-space: nowrap;
  -ms-overflow-style: none;
  /* IE and Edge */
  scrollbar-width: none;
  /* Firefox */
  flex: 0 0 auto;
  margin: 10px 0 5px 0;
}

.tab-container::-webkit-scrollbar {
  /* Chrome, Safari and Opera */
  display: none;
}

.tab-item {
  display: flex;
  height: auto;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
  margin-right: 20px;
  padding: 5px;
  border-radius: 10px;
  color: #727272;
  /* box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2); */
}

.tab-item-active {
  font-weight: 500;
  color: #fc9332;
}

.tab-item-active label {
  font-size: 14px !important;
}

.tab-item-active img {
  width: 60px !important;
  height: 60px !important;
  box-shadow: 0px 7px 13px rgba(0, 0, 0, 0.534);
}

.tab-item img {
  width: 50px;
  height: 50px;
  border-radius: 12px;
  margin-bottom: 3px;
  box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.336);
}

.tab-item label {
  font-size: 12px;
  font-family: "Sarabun";
  flex: 0 0 auto;
}

.content-container {
  flex-grow: 1;
  /* This will make it take up the remaining space */
  overflow-y: auto;
  /* This will make it scrollable if the content overflows */
}

.info img {
  width: 70px;
  height: 70px;
  border-radius: 5px;
  flex: 0 0 70px;
}

.drink {
  display: flex;
  justify-content: space-between;
  background: #ffffff;
  border-radius: 5px;
  margin-bottom: 25px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
}

.info {
  display: flex;
  flex: 1;
  padding: 10px;
}

.info-detail {
  text-align: left;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  flex: 1;
  padding: 0 15px 0 13px;
}

.info-detail label {
  color: #451717;
  font-size: 17px;
  font-weight: 700;
  margin-bottom: 3px;
}

.info-detail p {
  margin: 0;
  color: #696969;
  font-size: 12px;
  margin-bottom: 3px;
}

.info-detail span {
  color: #ff902a;
  font-size: 15px;
  font-weight: bold;
}

.el-icon-plus {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  flex: 0 0 55px;
  height: 32px;
  font-weight: bold;
  color: #ffffff;
  /* text-shadow: -0.3px -0.3px 0 #ffffff, 0.3px -0.3px 0 #ffffff,
    -0.3px 0.3px 0 #ffffff, 0.3px 0.3px 0 #ffffff; */
  background: #ff902a;
  border-bottom-left-radius: 14px;
  border-top-right-radius: 5px;
  -webkit-text-stroke: 1px white;
}
</style>
