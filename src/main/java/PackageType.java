/**
 * @author xiaohancheng
 * @company 广州市人心网络科技有限公司
 * @Description:
 * @date 2021/1/25
 * @Copyright (c) 2021, xiaohancheng@xinli001.com All Rights Reserved.
 */
public enum PackageType {
    REQUEST_PACK(1),
    RESPONSE_PACK(2);
    private int code;

    PackageType(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
