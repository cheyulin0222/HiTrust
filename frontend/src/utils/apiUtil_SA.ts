import ApiUtil from "./apiUtil_CRM";

class ApiUtil2 extends ApiUtil{
    
    url( path ) {
        if( !path.startsWith("/") ) {
            path = "/" + path;
        }
        return  '/' + this.env.AP_NAME + '/apiSA' + path;
    }
}

export default ApiUtil2 ;


