<template>
  <div class="popup-001">
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

<style>
.popup-overlay {
  position: fixed;
  top: 0;
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

.action .el-icon-minus {
  border: solid 1px #FF902A;
  color: #FF902A;
}

.action .el-icon-plus {
  background: #FF902A;
  color: #FF902A;
  color: #fff;
}

.action span {
  padding: 0 12px 3px 12px;
}

.size {
  width: 100%;
  display: flex;
  margin-top: 15px;
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

.popup-001 hr {
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

.submit .el-button--primary.is-plain:focus, .el-button--primary.is-plain:hover{
  background: #FF7A00;
  color: #fff;
}
</style>