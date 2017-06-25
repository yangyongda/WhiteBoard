package com.fjsd.yyd.whiteboard.util;


import com.fjsd.yyd.whiteboard.R;
import com.fjsd.yyd.whiteboard.bean.DrawPoint;
import com.fjsd.yyd.whiteboard.bean.WhiteBoardPoint;
import com.fjsd.yyd.whiteboard.bean.WhiteBoardPoints;

import java.util.ArrayList;
import java.util.List;



/**
 * 白板操作公共类
 *
 */

public class OperationUtils {
    /**
     * 操作类型-画笔
     */
    public static final int DRAW_PEN = 1;
    /**
     * 操作类型-文字
     */
    public static final int DRAW_TEXT = 2;
    /**
     * 操作类型-橡皮擦
     */
    public static final int DRAW_ERASER = 3;
    /**
     * 单例
     */
    private static OperationUtils mOperationStack;
    /**
     * 当前是否禁止白板操作
     */
    public boolean DISABLE = true;
    /**
     * 当前所在白板位置
     */
    public int mCurrentIndex = 0;
    /**
     * 每次操作的唯一标识
     */
    private long markId = 0;
    /**
     * 当前按钮点击
     */
    public int mCurrentOPerationPen = WhiteBoardVariable.Operation.PEN_CLICK;
    /**
     * 当前按钮点击
     */
    public int mCurrentOPerationColor = WhiteBoardVariable.Operation.COLOR_NORMAL;
    /**
     * 当前按钮点击
     */
    public int mCurrentOPerationText = WhiteBoardVariable.Operation.TEXT_NORMAL;
    /**
     * 当前按钮点击
     */
    public int mCurrentOPerationEraser = WhiteBoardVariable.Operation.ERASER_NORMAL;
    /**
     * 当前按钮点击
     */
    public int mCurrentOPerationBackColor = WhiteBoardVariable.Operation.BACKCOLOR_NORMAL;
    /**
     * 当前绘画类型：笔或者文字等
     */
    public int mCurrentDrawType = DRAW_PEN;

    /**
     * 当前颜色
     */
    public int mCurrentColor = WhiteBoardVariable.Color.ORANGE;
    /**
     * 当前画笔大小
     */
    public int mCurrentPenSize = WhiteBoardVariable.PenSize.MIDDLE;
    /**
     * 当前橡皮擦大小
     */
    public int mCurrentEraserSize = WhiteBoardVariable.EraserSize.MIDDLE;
    /**
     * 当前背景颜色
     */
    public int mCurrentBackColor = WhiteBoardVariable.BackColor.BACK1;
    /**
     * 白板操作集
     */
    private WhiteBoardPoints mWhiteBoardPoints;
    public interface  BackColor{
        int BACK1 =  AppContextUtil.getContext().getResources().getColor(R.color.white);
        int BACK2 =  AppContextUtil.getContext().getResources().getColor(R.color.back1);
        int BACK3 =  AppContextUtil.getContext().getResources().getColor(R.color.back2);
        int BACK4 =  AppContextUtil.getContext().getResources().getColor(R.color.back3);
    }
    /**
     * 单例
     */
    public static OperationUtils getInstance() {
        if (mOperationStack == null) {
            mOperationStack = new OperationUtils();
        }
        return mOperationStack;
    }

    /**
     * 私有实例化
     */
    private OperationUtils() {
    }

    /**
     * 初始化操作集
     */
    public void init() {
        DISABLE = true;
        mCurrentIndex = 0;
        markId = 0;
        mCurrentOPerationPen = WhiteBoardVariable.Operation.PEN_NORMAL;
        mCurrentOPerationColor = WhiteBoardVariable.Operation.COLOR_NORMAL;
        mCurrentOPerationText = WhiteBoardVariable.Operation.TEXT_NORMAL;
        mCurrentOPerationEraser = WhiteBoardVariable.Operation.ERASER_NORMAL;
        mCurrentOPerationBackColor = WhiteBoardVariable.Operation.BACKCOLOR_NORMAL;
        mCurrentDrawType = DRAW_PEN;
        mCurrentColor = WhiteBoardVariable.Color.ORANGE;
        mCurrentPenSize = WhiteBoardVariable.PenSize.MIDDLE;
        mCurrentEraserSize = WhiteBoardVariable.EraserSize.MIDDLE;
        mCurrentBackColor = WhiteBoardVariable.BackColor.BACK1;
        OperationUtils.getInstance().initDrawPointList();
    }

    /**
     * 返回指定白板的操作集
     */
    public WhiteBoardPoint getDrawPointList(int i) {
        if (null != mWhiteBoardPoints) {
            if (mWhiteBoardPoints.getWhiteBoardPoints().size() <= i) {
                WhiteBoardPoint drawPointList = new WhiteBoardPoint();
                drawPointList.setId(i);
                mWhiteBoardPoints.getWhiteBoardPoints().add(drawPointList);
                return getDrawPointList(i);
            } else {
                return mWhiteBoardPoints.getWhiteBoardPoints().get(i);
            }
        } else {
            mWhiteBoardPoints = new WhiteBoardPoints();
            mWhiteBoardPoints.setWhiteBoardPoints(new ArrayList<WhiteBoardPoint>());
            return getDrawPointList(i);
        }
    }

    /**
     * 返回指定白板的操作集大小
     */
    public int getDrawPointSize() {
        if (mWhiteBoardPoints != null && null != mWhiteBoardPoints.getWhiteBoardPoints()) {
            return mWhiteBoardPoints.getWhiteBoardPoints().size();
        } else {
            getDrawPointList(mCurrentIndex);
            return getDrawPointSize();
        }
    }

    /**
     * 返回指定白板的操作集
     */
    public WhiteBoardPoints getWhiteBoardPoints() {
        return mWhiteBoardPoints;
    }

    /**
     * 返回指定白板的操作集
     */
    public void setWhiteBoardPoints(WhiteBoardPoints whiteBoardPoints) {
        mWhiteBoardPoints = whiteBoardPoints;
    }

    /**
     * 初始化白板
     */
    public void initDrawPointList() {
        if (mWhiteBoardPoints != null && mWhiteBoardPoints.getWhiteBoardPoints() != null) {
            mWhiteBoardPoints.getWhiteBoardPoints().clear();
        } else {
            getDrawPointList(mCurrentIndex);
        }

    }

    /**
     * 新建白板
     */
    public void newPage() {
        mCurrentIndex = getDrawPointSize();
        getDrawPointList(mCurrentIndex);
    }


    /**
     * 获取每次操作的唯一标识
     */
    public long getNewMarkId() {
        return markId++;
    }

    /**
     * 获取当前白板操作集
     */
    public List<DrawPoint> getSavePoints() {
        return OperationUtils.getInstance().getDrawPointList(OperationUtils.getInstance().mCurrentIndex).getSavePoints();
    }

    /**
     * 获取当前白板删除操作集
     */
    public List<DrawPoint> getDeletePoints() {
        return OperationUtils.getInstance().getDrawPointList(OperationUtils.getInstance().mCurrentIndex).getDeletePoints();
    }


}
