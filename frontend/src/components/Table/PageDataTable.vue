<template>
	<SelfTable  ref="table"
    @updateSelData = "updateSelData"
    :options = "options"
    :dataRow = "dataRow"
    />
	<div class="row m-0" v-if="setPage">
        <div class="col-md-12 col-12 mt-2">
            <div class="d-flex justify-content-end">
			    <div class="d-flex"
			        style="align-items: center;">
			        <span class="px-2">{{ $t('component.pagination.total-of')}}</span>
			        <span class="px-2">{{ paginate.total }}</span>
			        <span class="px-2">筆</span>
			        <span class="px-2">{{ $t('component.pagination.display') }}</span>
			        <input class="btn
			            card
			            text-left w-25"
			            v-model="size"
			            style="min-height: 16px;"
			            maxlength="3"
			            onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'')"
			            @blur="pageKeyUp"
			            >

			        <span class="px-2">{{ $t('component.pagination.pens') }}</span>

			    </div>
			    <nav aria-label="Page navigation example">
			        <ul class="mb-0 pagination">
			            <li class="page-item"
			                :class="{disabled: page === 1}">
			                <a class="page-link"
			                    @click="doSelPage(page-1)">
			                    &lt;&lt; </a>
			            </li>
			            
            			<li class="page-item" v-for="p in pages"
                        :class="{disabled: ( p === page || p === '...' ) }" :key="p">
                            <a class="page-link"
                            @click="doSelPage(p)">
                                {{ p }}
                            </a>
            			</li>
			            
			            <li class="page-item"
			                :class="{disabled: page === maxPage}">
			                <a class="page-link"
			                @click="doSelPage(page+1)"> >> </a>
			            </li>
			        </ul>
			    </nav>
			</div>
        </div>
    </div>
</template>
<script >
import SelfTable from "./DataTable.vue";
/*
options : {
    colum : [ 
        {
            // 表格欄位
            type : 'checkBox'/'text'/'drop'/'button/input'
            value : [] , //表頭
            key : [], //欄位值，對應資料名稱
            colspan : 2 
            selOption : function ( payment ) checkBox用，可決定checkbox要不要顯示
            filter : function( value , key ) text用，把顯示的值在加工 key 是FOR 上面key使用，可對某一KEY
            classT:'11', //title的class
            classR:'22', // Row的class
            styleT:'11', //title的style
            styleR:'22', // Row的style
            func : [  drop 用， 顯示<span></span><a func >{text}</a>
                {
                    span : `<span class="material-icons pr-2 t-14">
                                reorder
                            </span>`, //span的HTML
                    text : 'easypay.button.detail | t ' , //span的HTML
                    func : function (payment) {
                        sip.onShowDetail(payment);
                    },
                } ,
                {
                    span : `<span class="material-icons pr-2 t-14">
                                do_not_disturb_on
                            </span>`,
                    text : 'easypay.button.stop | t ' ,
                    func : function (payment) {
                        alert('stop SIP transaction')
                    }
                }
            ]
            {
           
            button : [ //For Button
                {
                    class : "sip-status-undone rounded-pill" ,
                    key : "statusDesc" ,          //顯示的值，會對應rowdata
                    filter : function( payment ){ // 條件
                        return payment.statusDesc!='失敗'
                    }
                },
                {
                    class : "sip-status-error  rounded-pill mr-1" ,
                    key : "statusDesc" ,
                    filter : function( payment ){
                        return payment.statusDesc==='失敗'
                    }
                },
                {
                    class : "sip-status-resend rounded-pill" ,
                    key : "重送" ,
                    filter : function( payment ){
                        return payment.statusDesc==='失敗'
                    },
                    func : function ( payment ){
                        sip.resend(payment);
                    }
                },
                
            ]
        } 
    ],
    endButton : [
        {
            value : [""] ,
            icon: "fa-circle-plus" ,
            func : function ( payment ){
                _this.goTo({name:"StoreEdit",type:"add"})
            }
        }
    ],
}
*/
export default {
	data () {
        return {
            allSelected: false,
            paginate : this.options.paginate ,
            size : 10 ,
        };
    },
    props: {
        options : {
        	type : Object ,
        } ,
        dataRow : {
        	type : Array ,
        } ,
    },
    emits:["doSelPage","updateSelData"],
    model: {

    },
    components : {
        SelfTable
    },
    watch: {
        "paginate.size" (){
            if( this.size != this.paginate.size ) {
                this.size = this.paginate.size ;
            }
        },
        /*dataRow: function(){
            console.log(this.dataRow);
        },*/
    },
    computed: {
        setPage(){
            if( this.options.paginate ) {
                return true ;
            } else {
                return false ;
            }
        },
        page() {
            return this.paginate.page;
        },
        maxPage() {
            const { total , size } = this.paginate;
            return Math.ceil(total / size);
        },
        pages() {
            const { maxPage, page } = this;
            // 每次最多顯示5個頁碼
            var c = page
            var t = maxPage
            var p = []
            for (var i = 1; i <= t; i++) {
                p.push(i)
            }
            var l = p.length
            if (l <= 5) { // 總頁數<=5，顯示全部頁碼
                return p
            } else if (l > 5 && c <= 3) { // 總頁數>5 && 當前頁面<=3
                return [1, 2, 3, 4, '...', t]
            } else if (l > 5 && c >= 3 && c <= l - 2) { // 總頁數 > 5 && 當前頁面 > 3 && 當前頁 < 總頁數 - 3
                return [1, '...', c - 1, c , c + 1, '...', t]
            } else { // 總頁數 > 5 && 當前頁面 > 3 && 當前頁 > 總頁數 - 3
                return [1, '...', t - 3, t - 2, t - 1, t]
            }

        },
    },
    mounted() {

    },
    methods: {
        updateSelData ( selData ) {
             this.$emit("updateSelData", selData );
        },
        doSelPage ( page ) {
            this.$refs.table.reSetSel();
            this.allSelected = false;
            this.$emit('doSelPage', page);
        },
        resize: function () {
            return this.size ;
            //this.$emit('size', this.size);
        },
        pageKeyUp : function ( event ) {
            let page = this.page ;
            let total = this.total ;
            let size = this.size ;
            if( size == "" ) {
                this.paginate.size = this.size = size = 1
            } else {
                this.paginate.size = size ;
            }
            
            if( ( page - 1 ) * size < total + 1 ) {
                this.doSelPage(page)
            } else {
                this.doSelPage(1);
            }
            event.preventDefault()
        }
    },
}

</script>