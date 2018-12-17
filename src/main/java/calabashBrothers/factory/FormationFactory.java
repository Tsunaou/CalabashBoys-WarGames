package calabashBrothers.factory;
import calabashBrothers.formation.*;
/**
 * @ Author     ：Young
 * @ Date       ：Created in 14:49 2018/12/17
 * @ Description：产生阵型的工厂类
 */
public class FormationFactory {
    ChangShe createChangShe(int x, int y){
        return new ChangShe(x,y);
    }
    ChongE createChongE(int x, int y){
        return new ChongE(x,y);
    }
    FangYuan createFangYuan(int x, int y){
        return new FangYuan(x,y);
    }
    FengShi createFengShi(int x, int y){
        return new FengShi(x,y);
    }
    HeYi createHeYi(int x, int y){
        return new HeYi(x,y);
    }
    YanXing createYanXing(int x, int y){
        return new YanXing(x,y);
    }
    YanYue createYanYue(int x, int y){
        return new YanYue(x,y);
    }
    YuLin createYuLin(int x, int y){
        return new YuLin(x,y);
    }

}
