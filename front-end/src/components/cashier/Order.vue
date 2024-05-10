<template>
  <div class="order">
    <div class="cart"></div>
    <hr />
    <div class="category"></div>
    <hr />
    <div class="products">
      <el-row :gutter="20">
        <el-col v-for="product in products" :span="4.7" :key="product.id"
          ><Card :product="product" class="grid-content"
        /></el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import Card from "./Card.vue";

export default {
  components: {
    Card,
  },
  data() {
    return {
      products: [
        {
          id: 123,
          img: "https://cong-news.appwifi.com/wp-content/uploads/2023/02/co%CC%82%CC%81t-du%CC%9B%CC%80a-ca%CC%80-phe%CC%82_lo%CC%9B%CC%81n.png",
          name: "Cà phê cốt dừa",
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
          curSize: "S",
        },
        {
          id: 123,
          img: "https://cong-news.appwifi.com/wp-content/uploads/2023/02/co%CC%82%CC%81t-du%CC%9B%CC%80a-ca%CC%80-phe%CC%82_lo%CC%9B%CC%81n.png",
          name: "Cà phê cốt dừa",
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
          curSize: "S",
        },
        {
          id: 123,
          img: "https://cong-news.appwifi.com/wp-content/uploads/2023/02/co%CC%82%CC%81t-du%CC%9B%CC%80a-ca%CC%80-phe%CC%82_lo%CC%9B%CC%81n.png",
          name: "Cà phê cốt dừa",
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
          curSize: "S",
        },
        {
          id: 123,
          img: "https://cong-news.appwifi.com/wp-content/uploads/2023/02/co%CC%82%CC%81t-du%CC%9B%CC%80a-ca%CC%80-phe%CC%82_lo%CC%9B%CC%81n.png",
          name: "Cà phê cốt dừa",
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
          curSize: "S",
        },
        {
          id: 123,
          img: "https://cong-news.appwifi.com/wp-content/uploads/2023/02/co%CC%82%CC%81t-du%CC%9B%CC%80a-ca%CC%80-phe%CC%82_lo%CC%9B%CC%81n.png",
          name: "Cà phê cốt dừa",
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
          curSize: "S",
        },
        {
          id: 123,
          img: "https://cong-news.appwifi.com/wp-content/uploads/2023/02/co%CC%82%CC%81t-du%CC%9B%CC%80a-ca%CC%80-phe%CC%82_lo%CC%9B%CC%81n.png",
          name: "Cà phê cốt dừa",
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
          curSize: "S",
        },
        {
          id: 123,
          img: "https://cong-news.appwifi.com/wp-content/uploads/2023/02/co%CC%82%CC%81t-du%CC%9B%CC%80a-ca%CC%80-phe%CC%82_lo%CC%9B%CC%81n.png",
          name: "Cà phê cốt dừa",
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
          curSize: "S",
        },
        {
          id: 123,
          img: "https://cong-news.appwifi.com/wp-content/uploads/2023/02/co%CC%82%CC%81t-du%CC%9B%CC%80a-ca%CC%80-phe%CC%82_lo%CC%9B%CC%81n.png",
          name: "Cà phê cốt dừa",
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
          curSize: "S",
        },
      ],
    };
  },
};
</script>

<style scoped>
.order {
  display: flex;
  flex-direction: row;
  height: 100vh;
  /* background: yellow; */
}

.cart {
  width: 700px;
}

.category {
  width: 100px;
}

.products {
  width: calc(100% - 800px);
}

.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
  display: flex;
  justify-content: center;
}

.grid-content {
  min-height: 36px;
  margin-top: 10px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>
