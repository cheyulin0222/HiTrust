import  moment from 'moment'
import { useI18n } from "vue-i18n";
import i18n from '../i18n';
const { t } = i18n.global;
import $ from 'jquery';
import 'daterangepicker';
import 'daterangepicker/daterangepicker.css';

function mount(select, sdate, edate, cb , limitTime = false , timePicker = false , limitType = 'month') {
    var ranges = {};
    ranges[ t('time.today').toString()] = [moment(), moment()];
    ranges[ t('time.yesterday').toString()] = [moment().subtract(1, 'days'), moment().subtract(1, 'days')];
    ranges[ t('time.yesterday').toString()] = [moment().subtract(1, 'days'), moment().subtract(1, 'days')];

    ranges[ t('time.in-the-last-7day').toString()] = [moment().subtract(6, 'days'), moment()];
    ranges[ t('time.in-the-last-30day').toString()] = [moment().subtract(29, 'days'), moment()];
    ranges[ t('time.this-month').toString()] = [moment().startOf('month'), moment().endOf('month')];
    ranges[ t('time.last-month').toString()] = [
        moment().subtract(1, 'month').startOf('month'),
        moment().subtract(1, 'month').endOf('month'),
    ];
    $(select).daterangepicker(
        {
            timePicker: timePicker,
            startDate: sdate,
            endDate: edate,
            opens: 'left',
            ranges: ranges,
            locale: {
                customRangeLabel:  t('time.custom').toString(),
            },

        },
        function (s, e) {

            let strDate = new Date(s)
            let endDate = new Date(e)
            let result = parseInt(Math.abs(strDate - endDate) / 1000 / 60 / 60 / 24);
            if( limitTime ) {
                if( limitType === 'month' && result > 31 )  {
                    window.vue.config.globalProperties.showModel({
                        type : "error" , 
                        title : '時間範圍選取錯誤',
                        message : '時間範圍選取超過一個月'
                    });
                    return false ;
                } else if( limitType === 'year' && result > 365) {
                    window.vue.config.globalProperties.showModel({
                        type : "error" , 
                        title : '時間範圍選取錯誤',
                        message : '時間範圍選取超過一年'
                    });
                    return false ;
                } else {
                    cb(s, e);
                }
            } else {
                cb(s, e);
            }             
        }
    )
}

function unMount (select) {
   $(select).data('daterangepicker').remove();
}

function setStartDate( select , date ){
    $(select).data('daterangepicker').setStartDate(date);
}

function setEndDate( select , date ){
    $(select).data('daterangepicker').setEndDate(date);
}

export { mount , unMount , setStartDate , setEndDate };

