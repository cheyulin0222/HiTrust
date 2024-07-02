<template>
<div class="header-vue"
  v-cloak>

  <!-- 電腦版用 -->
  <header class="header-block pt-3"
    :class="isSub ? 'd-md-block d-none' : ''"
    v-show="!isSub">
    <div class="container-fluid">
      <div class="row">
        <div class="col-md col align-items-center mr-auto">
          <div class="d-flex flex-column">
            <h3 style="font-weight:bold;">
              {{ user.name }}    
            </h3>
            <ul class="ul-profile d-md-block d-none list-inline list-unstyled">
              <li class="list-inline-item t-10">{{ user.email }}   </li>
            </ul>
          </div>
        </div>
        <div class="col-md-auto col-auto align-items-center ml-auto">
          <ul class="ul-language d-md-block d-none list-inline list-unstyled">
            <!--<li class="list-inline-item"
              v-for="opt in langOptions"
              :class = "{ 'active' : ( opt.value === locale ) }"
              :key="opt.value">
              <a href="#"
                @click.prevent="onLocale(opt.value)">{{ opt.short }}</a>
            </li>-->
            <li class="list-inline-item">
              <a href="#"
                @click.prevent="logout()">
                <button type="button"
                  class="btn btn-outline-danger">{{ $t("header.link.logout")}}</button>
              </a>
            </li>
          </ul>
          <ul class="d-md-none d-block list-inline list-unstyled">
            <li class="list-inline-item">
              <router-link to = "/home/notification">
                <img src="@/assets/img/ic-notification.svg"
                  class="img-fluid">
              </router-link>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </header>
  <!-- 手機板用 
  <header class="header-block mobile-header d-md-none shadow-sm px-2 py-3 mb-3"
    v-show="isSub">
    <div class="container-fluid">
      <div class="row d-md-none d-flex">
        <div class="col-auto align-items-center">
          <a href="@"
            @click.prevent="onBack()"
            class="text-dark">
            <svg id="b5609b8a-cd81-4e02-b63c-8ebf90044476"
              data-name="back"
              xmlns="http://www.w3.org/2000/svg"
              width="9.09"
              height="14.75"
              viewBox="0 0 9.09 14.75">
              <title>back</title>
              <path d="M16,5,8,12l8,7"
                transform="translate(-7.24 -4.62)"
                fill="none"
                stroke="#000"
                stroke-opacity="0.6" />
            </svg>
          </a>
        </div>
        <div class="col align-items-center ml-auto pl-0">
          <p class="font-weight-bold mb-0">{{ subTitle }}</p>
        </div>
        <div class="col-auto align-items-center ml-auto pl-0">
          <p class="font-weight-bold mb-0"
            @click.prevent="onOption()">{{ optionLabel }}</p>
        </div>
      </div>
    </div>
  </header>
  <div class="header-block mobile-header-filling d-md-none py-3"
    v-class.mb-3="subBottomPadding"
    v-show="isSub">
    &nbsp;
  </div>-->
</div>
</template>

<script>
import { useI18n } from 'vue-i18n';
import { mapGetters, mapState } from 'vuex';
import { getLocale , setLocale } from "@/utils/user"
import { sendLogOut } from "@/utils/session"
import i18n from '@/i18n'

export default {
  data () {
    return {
      isSub: false,
      subBottomPadding: true,
      subTitle: '',
      optionLabel: '',
      callbacks: {
          onBack: null,
          onOption: null,
      },
      apName: '',
    }
  },
  computed: {
      lang() {
          return window.document.body.parentElement.lang;
      },
      langOptions: function () {
          return [
              {
                  label: '中文',
                  short: '中',
                  value: 'zh-tw',
              },
              {
                  label: 'English',
                  short: 'En',
                  value: 'en',
              },
          ];
      },
      ...mapGetters( 'user' , {
        user : "getUser",
        locale : "getLocale"
      }),
      loc : {
        get() {
          return this.locale ;
        }
      }
  },
  mounted: function () {
      this.initData();
  },
  methods: {

      initData: function () {
        i18n.global.locale = this.$store.getters["user/getLocale"]
      },
      setSubHeader: function (subTitle, optionLabel, options) {
        var opts = options || {};
        this.subTitle = subTitle;
        this.subBottomPadding = 'subBottomPadding' in opts ? opts.subBottomPadding : true;
        this.optionLabel = optionLabel;
        this.callbacks.onBack = opts.onBack || null;
        this.callbacks.onOption = opts.onOption || null;
        this.isSub = true;
      },
      clear: function () {
        this.subTitle = '';
        this.optionLabel = '';
        this.callbacks = {};
        this.isSub = false;
      },
      isMobile: function () {
        return window.outerWidth < 767;
      },
      onBack: function () {
        if (this.callbacks.onBack) {
            this.callbacks.onBack();
        } else {
            window.history.back();
        }
      },
      getEnvs: function () {
        ajax.get('/envs').then(function (res) {
            this.ap_name = res.AP_NAME;
        });
      },
      logout: function () {
        sendLogOut();
      },

      onLocale(loc) {
        if( loc != this.locale ) {
          setLocale(loc);
        }
        
      },
      onOption: function () {
        if (this.callbacks.onOption) {
            this.callbacks.onOption();
        }
      },
    },
  props: {
    msg: String
  },
  watch:{
    loc (newV,oldV){
      i18n.global.locale = newV
    }
  },
  setup(props, context) {
    const { locale } = useI18n()
    const handleChangeLanguage = (e) => {
      locale.value = e.target.value;
    };
    return {
      handleChangeLanguage
    }
  },

}
</script>