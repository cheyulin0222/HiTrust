<template>
  <div class="container-fluid">
    <h4 class="sec-title font-weight-bold mt-md-4 mb-3">收據產生器</h4>
    <div class="row p-3 mb-3">
      <div class="col-md-2 mb-2">
        <div class="fw-bold">商品名稱</div>
      </div>

      <div class="col-md-2 mb-2">
        <div class="fw-bold">單價</div>
      </div>

      <div class="col-md-2 mb-2">
        <div class="fw-bold">數量</div>
      </div>

      <div class="col-md-2 mb-2">
        <div class="fw-bold">地點</div>
      </div>

      <div class="col-md-2 mb-2">
        <div class="fw-bold">品項</div>
      </div>
    </div>

    <div class="row p-3">
      <div class="col-md-2 mb-2">
        <input type="text" class="form-control"  v-model="name1" :maxlength="20">
      </div>
      <div class="col-md-2 mb-2">
        <input type="number" class="form-control"  v-model="price1" :maxlength="50">
      </div>
      <div class="col-md-2 mb-2">
        <input type="number" class="form-control"  v-model="quantity1">
      </div>
      <div class="col-md-2 mb-2">
        <select class="form-select" v-model="location1">
          <option value="CA">加州</option>
          <option value="NY">紐約</option>
        </select>
      </div>
      <div class="col-md-2 mb-2">
        <select class="form-select" v-model="type1">
          <option value="OTHER">其他</option>
          <option value="CLOTHING">衣服</option>
          <option value="FOOD">食物</option>
        </select>
      </div>
    </div>

    <div class="row p-3">
      <div class="col-md-2 mb-2">
        <input type="text" class="form-control" v-model="name2" :maxlength="20">
      </div>
      <div class="col-md-2 mb-2">
        <input type="number" class="form-control" v-model="price2" :maxlength="50">
      </div>
      <div class="col-md-2 mb-2">
        <input type="number" class="form-control" id="price2" v-model="quantity2">
      </div>
      <div class="col-md-2 mb-2">
        <select class="form-select" v-model="location2">
          <option value="CA">加州</option>
          <option value="NY">紐約</option>
        </select>
      </div>
      <div class="col-md-2 mb-2">
        <select class="form-select" v-model="type2">
          <option value="OTHER">其他</option>
          <option value="CLOTHING">衣服</option>
          <option value="FOOD">食物</option>
        </select>
      </div>
    </div>

    <div class="row mt-2 mb-5 justify-content-center"> 
      <div class="col-md-4 text-center">
        <button type="button" @click.prevent="genReceipt"
                class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
          <span class="mx-2">送出</span>
        </button>
      </div>
    </div>

    <div class="row p-3 mb-3">
      <div class="col-md-4 mb-2 fw-bold">商品名稱</div>
      <div class="col-md-4 mb-2 fw-bold">單價</div>
      <div class="col-md-4 mb-2 fw-bold">數量</div>
    </div>

    <div class="row p-3" v-for="(item, index) in response.shoppingList" :key="index">
      <div class="col-md-4 mb-2">{{ item.productName }}</div>
      <div class="col-md-4 mb-2">{{ item.price}}</div>
      <div class="col-md-4 mb-2">{{ item.quantity }}</div>
    </div>

    <div class="row p-3">
      <div class="col-md-4 mb-2 fw-bold">小計</div>
      <div class="col-md-4 mb-2">{{ response.subtotal}}</div>
    </div>
    <div class="row p-3">
      <div class="col-md-4 mb-2 fw-bold">稅</div>
      <div class="col-md-4 mb-2">{{ response.tax}}</div>
    </div>
    <div class="row p-3">
      <div class="col-md-4 mb-2 fw-bold">總計</div>
      <div class="col-md-4 mb-2">{{response.total}}</div>
    </div>

  </div>


</template>

<script>
import axios from 'axios';

export default {
  data(){
    return {
      name1 : "",
      price1 : 0,
      quantity1: 0,
      location1 : "CA",
      type1 : "OTHER",
      name2 : "",
      price2 : 0,
      quantity2: 0,
      location2 : "CA",
      type2 : "OTHER",
      response : {}
      
    }
  },
  methods: {
    genReceipt() {
      let list = [
        {
          productName : this.name1,
          price : this.price1,
          quantity : this.quantity1,
          location : this.location1,
          type : this.type1,
        },
        {
          productName : this.name2,
          price : this.price2,
          quantity : this.quantity2,
          location : this.location2,
          type : this.type2,
        },
      ];

      let data = {
        shoppingList : list
      }

      console.log(data);
      let _this = this;
      axios.post('/api/gen-receipt', data)
        .then(response => {
          _this.response = response.data;
        })
        .catch(error => {
          console.error('Error:', error);
        });
        
      
    },
  }
}
</script>
