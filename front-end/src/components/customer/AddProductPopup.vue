<template>
  <div>
    <div v-if="isPopupVisible" class="popup-overlay" @click.self="closePopup">
      <div class="popup-content">
        <!-- Nội dung của popup -->
        <div class="header">
          <label for="">Thêm món mới</label>
          <!-- <i class="el-icon-close" @click="closePopup"></i> -->
        </div>
        <hr>
        <div class="product">
          <div class="info">
            <img :src="product.image" alt="" />
            <div class="info-detail">
              <label>{{ product.name }}</label>
              <p>{{ product.sumary }}</p>
              <span>{{ curPrice }}</span>
            </div>
          </div>
          <div class="action">
            <i class="el-icon-minus icon"></i>
            <span>{{ quantity }}</span>
            <i class="el-icon-plus icon"></i>
          </div>
        </div>
        <hr>
        <div class="size">
          <el-radio-group v-model="size">
            <div v-for="price in product.price" :key="price.size" class="size-item">
              <el-radio :label="price.size">
                <span style="margin-right: 40px;">{{ "Size " + price.size }}</span>
                <span>{{ price.value }}</span>
              </el-radio>
            </div>

          </el-radio-group>
        </div>
        <hr>
        <div class="note">
          <el-input type="textarea" :rows="2" placeholder="Ghi chú cho quán..." v-model="note">
          </el-input>
        </div>
        <div class="submit">
          <el-button type="primary" plain>{{ "Thêm vào - 36,000 đ"}}</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["isPopupVisible", "productId"],
  data() {
    return {
      quantity: 1,
      size: "S",
      note: "",
      product: {
        id: 123,
        img: "https://cong-news.appwifi.com/wp-content/uploads/2023/02/co%CC%82%CC%81t-du%CC%9B%CC%80a-ca%CC%80-phe%CC%82_lo%CC%9B%CC%81n.png",
        name: "Cà phê cốt dừa",
        sumary: "Cà phê cốt dừa ngon tuyệt",
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
    };
  },
  methods: {
    closePopup() {
      this.$emit("close", false);
    },
  },
  computed: {
    curPrice() {
      const result = this.product.price.find(p => p.size === this.size
      );
      return result.value;
    },
  },
};
</script>