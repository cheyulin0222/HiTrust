<template>

<div id="wrapper"
    class="d-flex login-wrapper justify-content-center align-items-md-start align-items-center">
    <div id="page-content-wrapper"
        v-cloak
        class="login-vue">

        <header class="header-block px-2 py-3 mb-3">
            <div class="container">
                <div class="row">
                    <div class="col-12 align-items-center">
                        <div class="d-flex justify-content-md-start justify-content-center">
                            <a href="#">
                                <img src="../assets/img/logo_hitrust.svg"
                                    class="img-fluid">
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </header>
        <section class="app-view justify-content-center align-items-md-start align-items-center mt-md-5">
            <div class="container">
                <div class="row">
                    <div class="col-md-5 col-12 mx-auto">
                        <h2 class="login-title font-weight-bold text-center mb-3">HiTRUSTpay 後端管理系統</h2>
                        <form id=""
                            class="form-signin mb-4"
                            @submit.prevent="onLogin">
                            <div class="card border-0 h-shadow">
                                <div class="card-body py-5">
                                    <div class="form-label-group position-relative mb-2">
                                        <input type="text"
                                            id="inputEmail"
                                            class="form-control"
                                            :placeholder="$t('login.label.input-username')"
                                            required
                                            v-model="input.email"
                                            autofocus />
                                        <label for="inputEmail"
                                            hidden>{{ $t('login.label.input-username') }}</label>
                                        <span class="login-left"><img src="../assets/img/ic-user.svg"
                                                class="img-fluid" /></span>
                                    </div>

                                    <div class="form-label-group custom-control custom-checkbox mb-3">
                                        <input type="checkbox"
                                            class="custom-control-input"
                                            id="rememberMe"
                                            v-model="rememberEmail" />
                                        <label class="custom-control-label t-12"
                                            for="rememberMe">{{ $t('login.label.remember-me') }}</label>
                                    </div>

                                    <div class="form-label-group position-relative mb-2">
                                        <input :type="visiblePassword ? 'text' : 'password'"
                                            id="inputPassword"
                                            class="form-control"
                                            :placeholder="$t('login.label.input-password')"
                                            v-model="input.password"
                                            required />
                                        <label for="inputPassword"
                                            hidden>{{ $t('login.label.input-password')}}</label>
                                        <span class="login-left"><img src="../assets/img/ic-pw.svg"
                                                class="img-fluid" /></span>
                                        <span class="login-right">
                                            <button type="button"
                                                class="btn btn-link btn-showpw"
                                                @click="onToggleVisiblePassword()">
                                                <i class="fa custom-eye"
                                                    :class="[visiblePassword ? 'custom-eye-slash' : 'custom-eye']"></i>
                                            </button>
                                        </span>
                                    </div>

                                    <!--<div class="form-label-group custom-control custom-checkbox mb-3">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <input type="checkbox"
                                                v-show="isApp"
                                                class="custom-control-input"
                                                v-model="input.autologin"
                                                id="autoLogin" />
                                            <label class="custom-control-label t-12"
                                                v-show="isApp"
                                                for="autoLogin">{{$t('login.label.auto-login-with-7d') }}</label>
                                            <a href="{%=__.apUrl('/forgot') %}"
                                                class="text-oceanblue t-12">{{ $t('login.link.forgot-password') }}</a>
                                        </div>
                                    </div>-->
                                    <div class="col-12"
                                        v-show="errorMessage">
                                        <div class="alert alert-danger error-message"
                                            v-show="errorMessage">
                                            {{ errorMessage }}
                                        </div>
                                    </div>

                                    <div class="mt-4 px-4">
                                        <button type="submit"
                                            :disabled="loading"
                                            class="btn btn-danger btn-block d-flex justify-content-center align-items-center">
                                            <i class="material-icons loading"
                                                v-if="loading">refresh</i>
                                            <span class="mx-2">{{ $t('login.button.login') }}</span>
                                        </button>
                                    </div>

                                    <div class="mt-4 px-4"
                                        v-if="quickLogin && isApp">

                                        <button type="button"
                                            class="btn btn-link btn-block d-flex justify-content-center align-items-center"
                                            @click.prevent="checkAutologin">
                                            <img class="quick-login-icon"
                                                src="../assets/img/fingerprint.png">
                                            <span class="mx-2">{{ $t('login.button.quick-login') }}</span>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>

                        

                    </div>
                </div>
            </div>
            <!--
            <autologin-vue ref="autologin"
                @fail="onAutologinFail"
                @done="onAutologinDone"></autologin-vue>
                -->
        </section>
        
    </div>
</div>
</template>
<script>
import { Options, Vue } from "vue-class-component";
import { setUser , setMenu , getSHAOldPassword } from '@/utils/user'
import axios from 'axios' ;
//import router from "./router";
export default {
    data() {
        return{
            input: {
                email: '',
                password: '',
                autologin: false,
            },
            visiblePassword: false,
            errorTimer: 0,
            errorMessage: '',
            rememberEmail: false,
            hasToken: false,
            loading: false,
            quickLogin: false,
            apName: '',
            deviceType: '',
            oldSC: ''
        }
    },
    computed: {
        isApp() {
            return false ;
        }
    },
    methods : {
        onToggleVisiblePassword: function () {
            this.visiblePassword = !this.visiblePassword;
        },
        onLogin: function () {
            var input = this.input;
            if (this.loading) {
                return;
            }
            this.errorMessage = '';
            this.loading = true;
            /*
            "
                account": "averytsao@hitrust.com.tw",
                "password": "3edc4rfv"
            */
            let _this = this ;
            this.ApiUtilSA.post("/user/login",{
                "userId": input.email,
                "userPassword": getSHAOldPassword( input.password )
            },undefined,false)
            .then((res) => {
                if( res.success ) {
                    let user = res.data ; 
                
                    return this.ApiUtilSA.get("/get-all-menu",null,{Authorization: `Bearer ${user.loginToken}`},false )
                    .then( ( res ) => {
                        if( res.success ) {
                            setUser({
                                email   : user.userId ,
                                token   : user.loginToken ,
                                name    : user.userName
                            });
                            setMenu(res.data);
                            this.showModel({
                                title : '登入',
                                message : '登入成功',
                                endFunc : function(){
                                    //登入驗證
                                    this.$router.push('/home');
                                }.bind(this),
                            });   
                        }
                        
                    });
                }                
            })
            .finally(function () {
                _this.loading = false;
            });
            
        },
    }

}

</script>