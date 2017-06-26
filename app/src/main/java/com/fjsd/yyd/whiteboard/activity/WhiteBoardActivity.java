package com.fjsd.yyd.whiteboard.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fjsd.yyd.whiteboard.R;
import com.fjsd.yyd.whiteboard.util.AppContextUtil;
import com.fjsd.yyd.whiteboard.util.OperationUtils;
import com.fjsd.yyd.whiteboard.util.StoreUtil;
import com.fjsd.yyd.whiteboard.util.WhiteBoardVariable;
import com.fjsd.yyd.whiteboard.view.DrawPenView;
import com.fjsd.yyd.whiteboard.view.DrawTextLayout;
import com.fjsd.yyd.whiteboard.view.FloatingActionsMenu;
import com.fjsd.yyd.whiteboard.view.FloatingImageButton;

import net.margaritov.preference.colorpicker.ColorPickerDialog;

import java.io.File;
import java.io.FileOutputStream;



public class WhiteBoardActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView mIvWhiteBoardBook;
    private RelativeLayout mRlHead;
    private TextView mTvWhiteBoardHead;
    private ImageView mIvWhiteBoardExport;
    private ImageView mIvWhiteBoardSave;
    private FrameLayout mFlView;
    private DrawPenView mDbView;
    private DrawTextLayout mDtView;
    private FloatingActionsMenu mFabMenuSize;
    private FloatingImageButton mBtSizeLarge;
    private FloatingImageButton mBtSizeMiddle;
    private FloatingImageButton mBtSizeMini;
    private FloatingActionsMenu mFabMenuColor;
    private FloatingImageButton mBtColorGreen;
    private FloatingImageButton mBtColorPurple;
    private FloatingImageButton mBtColorPink;
    private FloatingImageButton mBtColorOrange;
    private FloatingImageButton mBtColorBlack;
    private FloatingImageButton mBtColorPick;
    private FloatingActionsMenu mFabMenuEraser;
    private FloatingImageButton mBtEraserLarge;
    private FloatingImageButton mBtEraserMiddle;
    private FloatingImageButton mBtEraserMini;
    private FloatingImageButton mBtEraserSpace;
    private FloatingActionsMenu mFabMenuBack;
    private FloatingImageButton mBtBack1;
    private FloatingImageButton mBtBack2;
    private FloatingImageButton mBtBack3;
    private FloatingImageButton mBtBack4;
    private ImageView mIvWhiteBoardUndo;
    private ImageView mIvWhiteBoardRedo;
    private LinearLayout mLlWhiteBoardPage;
    private ImageView mIvWhiteBoardPre;
    private TextView mTvWhiteBoardPage;
    private ImageView mIvWhiteBoardNext;
    private ImageView mIvWhiteBoardAdd;
    private ImageView mIvWhiteBoardDisable;
    private ImageView mIvWhiteBoardQuit;
    private ImageView mIvWhiteBoardConfirm;
    private RelativeLayout mRlContent;
    private RelativeLayout mRlBottom;
    private View mVBottomBack;
    private LinearLayout mLlWhiteBoardPre;
    private LinearLayout mLlWhiteBoardNext;
    private ImageView mIvWhiteBoardBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white_board);
        initView();
        initEvent();
    }

    private void initView() {
        findView();
        changePenBack();
        changeColorBack();
        changeEraserBack();
        ToolsOperation(WhiteBoardVariable.Operation.PEN_NORMAL);
        mDbView.post(new Runnable() {
            @Override
            public void run() {
                showPoints();
            }
        });

    }
    private void initEvent() {
        //头部
        mIvWhiteBoardBack.setOnClickListener(this);
        mIvWhiteBoardExport.setOnClickListener(this);
        mIvWhiteBoardSave.setOnClickListener(this);
        mIvWhiteBoardQuit.setOnClickListener(this);
        mIvWhiteBoardConfirm.setOnClickListener(this);
        mVBottomBack.setOnClickListener(this);
        //画笔尺寸大小
        mFabMenuSize.setOnFloatingActionsMenuClickListener(new FloatingActionsMenu.OnFloatingActionsMenuClickListener() {
            @Override
            public void addButtonLister() {
                ToolsOperation(WhiteBoardVariable.Operation.PEN_CLICK);
            }
        });
        mBtSizeLarge.setOnClickListener(this);
        mBtSizeMiddle.setOnClickListener(this);
        mBtSizeMini.setOnClickListener(this);
        //画笔颜色
        mFabMenuColor.setOnFloatingActionsMenuClickListener(new FloatingActionsMenu.OnFloatingActionsMenuClickListener() {
            @Override
            public void addButtonLister() {
                ToolsOperation(WhiteBoardVariable.Operation.COLOR_CLICK);
            }
        });
        mBtColorGreen.setOnClickListener(this);
        mBtColorPurple.setOnClickListener(this);
        mBtColorPink.setOnClickListener(this);
        mBtColorOrange.setOnClickListener(this);
        mBtColorBlack.setOnClickListener(this);
        mBtColorPick.setOnClickListener(this);

        //橡皮擦尺寸大小
        mFabMenuEraser.setOnFloatingActionsMenuClickListener(new FloatingActionsMenu.OnFloatingActionsMenuClickListener() {
            @Override
            public void addButtonLister() {
                ToolsOperation(WhiteBoardVariable.Operation.ERASER_CLICK);
            }
        });
        mBtEraserLarge.setOnClickListener(this);
        mBtEraserMiddle.setOnClickListener(this);
        mBtEraserMini.setOnClickListener(this);
        mBtEraserSpace.setOnClickListener(this);

        /*背景颜色
        * */
        mFabMenuBack.setOnFloatingActionsMenuClickListener(new FloatingActionsMenu.OnFloatingActionsMenuClickListener() {
            @Override
            public void addButtonLister() {
                ToolsOperation(WhiteBoardVariable.Operation.BACKCOLOR_CLICK);
            }
        });
        mBtBack1.setOnClickListener(this);
        mBtBack2.setOnClickListener(this);
        mBtBack3.setOnClickListener(this);
        mBtBack4.setOnClickListener(this);

        mIvWhiteBoardUndo.setOnClickListener(this);
        mIvWhiteBoardRedo.setOnClickListener(this);

        mLlWhiteBoardPre.setOnClickListener(this);
        mIvWhiteBoardPre.setOnClickListener(this);
        mLlWhiteBoardNext.setOnClickListener(this);
        mIvWhiteBoardNext.setOnClickListener(this);
        mIvWhiteBoardAdd.setOnClickListener(this);
        mIvWhiteBoardDisable.setOnClickListener(this);

    }
    /*
    * 获取控件
    * */
    private void findView() {
        mIvWhiteBoardBook = (ImageView) findViewById(R.id.iv_white_board_book);
        mRlHead = (RelativeLayout) findViewById(R.id.rl_head);
        mTvWhiteBoardHead = (TextView) findViewById(R.id.tv_white_board_head);
        mIvWhiteBoardExport = (ImageView) findViewById(R.id.iv_white_board_export);
        mIvWhiteBoardSave = (ImageView) findViewById(R.id.iv_white_board_save);
        mFlView = (FrameLayout) findViewById(R.id.fl_view);
        mDbView = (DrawPenView) findViewById(R.id.db_view);
        mBtSizeLarge = (FloatingImageButton) findViewById(R.id.bt_size_large);
        mBtSizeMiddle = (FloatingImageButton) findViewById(R.id.bt_size_middle);
        mBtSizeMini = (FloatingImageButton) findViewById(R.id.bt_size_mini);
        mFabMenuColor = (FloatingActionsMenu) findViewById(R.id.fab_menu_color);
        mBtEraserLarge = (FloatingImageButton) findViewById(R.id.bt_eraser_large);
        mBtEraserMiddle = (FloatingImageButton) findViewById(R.id.bt_eraser_middle);
        mBtEraserMini = (FloatingImageButton) findViewById(R.id.bt_eraser_mini);
        mFabMenuSize = (FloatingActionsMenu) findViewById(R.id.fab_menu_size);
        mFabMenuColor = (FloatingActionsMenu) findViewById(R.id.fab_menu_color);
        mVBottomBack = findViewById(R.id.v_bottom_back);
        mDtView = (DrawTextLayout)findViewById(R.id.dt_view);
        mBtColorGreen = (FloatingImageButton)findViewById(R.id.bt_color_green);
        mBtColorPurple = (FloatingImageButton)findViewById(R.id.bt_color_purple);
        mBtColorPink = (FloatingImageButton)findViewById(R.id.bt_color_pink);
        mBtColorOrange = (FloatingImageButton)findViewById(R.id.bt_color_orange);
        mBtColorBlack = (FloatingImageButton)findViewById(R.id.bt_color_black);
        mFabMenuEraser = (FloatingActionsMenu)findViewById(R.id.fab_menu_eraser);
        mIvWhiteBoardUndo = (ImageView)findViewById(R.id.iv_white_board_undo);
        mIvWhiteBoardRedo = (ImageView)findViewById(R.id.iv_white_board_redo);
        mLlWhiteBoardPage = (LinearLayout)findViewById(R.id.ll_white_board_page);
        mIvWhiteBoardPre = (ImageView)findViewById(R.id.iv_white_board_pre);
        mTvWhiteBoardPage = (TextView)findViewById(R.id.tv_white_board_page);
        mIvWhiteBoardNext = (ImageView)findViewById(R.id.iv_white_board_next);
        mIvWhiteBoardAdd = (ImageView)findViewById(R.id.iv_white_board_add);
        mIvWhiteBoardDisable = (ImageView)findViewById(R.id.iv_white_board_disable);
        mIvWhiteBoardQuit = (ImageView)findViewById(R.id.iv_white_board_quit);
        mIvWhiteBoardConfirm = (ImageView)findViewById(R.id.iv_white_board_confirm);
        mRlContent = (RelativeLayout) findViewById(R.id.rl_content);
        mRlBottom = (RelativeLayout)findViewById(R.id.rl_bottom);
        mLlWhiteBoardPre = (LinearLayout)findViewById(R.id.ll_white_board_pre);
        mLlWhiteBoardNext = (LinearLayout)findViewById(R.id.ll_white_board_next);
        mIvWhiteBoardBack = (ImageView)findViewById(R.id.iv_white_board_back);
        mBtEraserSpace = (FloatingImageButton)findViewById(R.id.bt_eraser_space);
        mBtColorPick = (FloatingImageButton)findViewById(R.id.bt_color_pick);
        mFabMenuBack = (FloatingActionsMenu)findViewById(R.id.fab_menu_back);
        mBtBack1 = (FloatingImageButton)findViewById(R.id.bt_back1);
        mBtBack2 = (FloatingImageButton)findViewById(R.id.bt_back2);
        mBtBack3 = (FloatingImageButton)findViewById(R.id.bt_back3);
        mBtBack4 = (FloatingImageButton)findViewById(R.id.bt_back4);
    }

    /**
     * 切换画笔尺寸
     */
    private void changePenBack() {
        //圆代表笔的粗细，外圈代表选中状态
        if (OperationUtils.getInstance().mCurrentPenSize == WhiteBoardVariable.PenSize.LARRGE) {
            mBtSizeLarge.drawCircleAndRing(WhiteBoardVariable.PenSize.LARRGE, OperationUtils.getInstance().mCurrentColor);
            mBtSizeMiddle.drawCircle(WhiteBoardVariable.PenSize.MIDDLE);
            mBtSizeMini.drawCircle(WhiteBoardVariable.PenSize.MINI);
        } else if (OperationUtils.getInstance().mCurrentPenSize == WhiteBoardVariable.PenSize.MIDDLE) {
            mBtSizeLarge.drawCircle(WhiteBoardVariable.PenSize.LARRGE);
            mBtSizeMiddle.drawCircleAndRing(WhiteBoardVariable.PenSize.MIDDLE, OperationUtils.getInstance().mCurrentColor);
            mBtSizeMini.drawCircle(WhiteBoardVariable.PenSize.MINI);
        } else if (OperationUtils.getInstance().mCurrentPenSize == WhiteBoardVariable.PenSize.MINI) {
            mBtSizeLarge.drawCircle(WhiteBoardVariable.PenSize.LARRGE);
            mBtSizeMiddle.drawCircle(WhiteBoardVariable.PenSize.MIDDLE);
            mBtSizeMini.drawCircleAndRing(WhiteBoardVariable.PenSize.MINI, OperationUtils.getInstance().mCurrentColor);

        }
    }

    /**
     * 切换颜色控制按钮背景
     */
    private void changeColorBack() {
        if (OperationUtils.getInstance().mCurrentColor == WhiteBoardVariable.Color.BLACK) {
            mFabMenuColor.setAddButtonBackground(R.drawable.white_board_color_black_selector);
        } else if (OperationUtils.getInstance().mCurrentColor == WhiteBoardVariable.Color.ORANGE) {
            mFabMenuColor.setAddButtonBackground(R.drawable.white_board_color_orange_selector);
        } else if (OperationUtils.getInstance().mCurrentColor == WhiteBoardVariable.Color.PINK) {
            mFabMenuColor.setAddButtonBackground(R.drawable.white_board_color_pink_selector);
        } else if (OperationUtils.getInstance().mCurrentColor == WhiteBoardVariable.Color.PURPLE) {
            mFabMenuColor.setAddButtonBackground(R.drawable.white_board_color_purple_selector);
        } else if (OperationUtils.getInstance().mCurrentColor == WhiteBoardVariable.Color.GREEN) {
            mFabMenuColor.setAddButtonBackground(R.drawable.white_board_color_green_selector);
        }else{
            mFabMenuColor.setAddButtonBackground(R.drawable.pickcolor);
        }
    }

    /**
     * 切换橡皮擦尺寸按钮背景
     */
    private void changeEraserBack() {
        //圆代表橡皮的粗细，外圈代表选中状态
        if (OperationUtils.getInstance().mCurrentEraserSize == WhiteBoardVariable.EraserSize.LARRGE) {
            mBtEraserLarge.drawCircleAndRing(WhiteBoardVariable.EraserSize.LARRGE, WhiteBoardVariable.Color.BLACK);
            mBtEraserMiddle.drawCircle(WhiteBoardVariable.EraserSize.MIDDLE, WhiteBoardVariable.Color.BLACK);
            mBtEraserMini.drawCircle(WhiteBoardVariable.EraserSize.MINI, WhiteBoardVariable.Color.BLACK);
        } else if (OperationUtils.getInstance().mCurrentEraserSize == WhiteBoardVariable.EraserSize.MIDDLE) {
            mBtEraserLarge.drawCircle(WhiteBoardVariable.EraserSize.LARRGE, WhiteBoardVariable.Color.BLACK);
            mBtEraserMiddle.drawCircleAndRing(WhiteBoardVariable.EraserSize.MIDDLE, WhiteBoardVariable.Color.BLACK);
            mBtEraserMini.drawCircle(WhiteBoardVariable.EraserSize.MINI, WhiteBoardVariable.Color.BLACK);
        } else if (OperationUtils.getInstance().mCurrentEraserSize == WhiteBoardVariable.EraserSize.MINI) {
            mBtEraserLarge.drawCircle(WhiteBoardVariable.EraserSize.LARRGE, WhiteBoardVariable.Color.BLACK);
            mBtEraserMiddle.drawCircle(WhiteBoardVariable.EraserSize.MIDDLE, WhiteBoardVariable.Color.BLACK);
            mBtEraserMini.drawCircleAndRing(WhiteBoardVariable.EraserSize.MINI, WhiteBoardVariable.Color.BLACK);

        }
    }


    /**
     * 白板工具栏点击切换操作
     */
    private void ToolsOperation(int currentOperation) {
        setPenOperation(currentOperation);  //设置画笔菜单操作
        setColorOperation(currentOperation);
        setEraserOperation(currentOperation);
        setBackColorOperation(currentOperation);
        showOutSideView();
    }

    /**
     * 白板工具栏点击切换操作-画笔
     */
    private void setPenOperation(int currentOperation) {
        switch (currentOperation) {
            case WhiteBoardVariable.Operation.PEN_CLICK: //点击画笔菜单
                switch (OperationUtils.getInstance().mCurrentOPerationPen) {  //下面是子菜单事件（菜单已经展开）
                    case WhiteBoardVariable.Operation.PEN_NORMAL:  //画笔菜单项没点击
                        OperationUtils.getInstance().mCurrentDrawType = OperationUtils.DRAW_PEN;  //画笔
                        mDbView.setPaint(null);   //初始化画笔
                        mFabMenuSize.setAddButtonBackground(R.drawable.white_board_pen_selected_selector); //设置笔大小选项背景
                        OperationUtils.getInstance().mCurrentOPerationPen = WhiteBoardVariable.Operation.PEN_CLICK;
                        break;
                    case WhiteBoardVariable.Operation.PEN_CLICK:  //画笔菜单项被点击（即选中了）
                        mFabMenuSize.expand();
                        changePenBack();
                        OperationUtils.getInstance().mCurrentOPerationPen = WhiteBoardVariable.Operation.PEN_EXPAND;
                        break;
                    case WhiteBoardVariable.Operation.PEN_EXPAND: //之前菜单式展开状态，所以点击后就折叠
                        mFabMenuSize.collapse(); //折叠
                        OperationUtils.getInstance().mCurrentOPerationPen = WhiteBoardVariable.Operation.PEN_CLICK;
                        break;
                }
                break;
            case WhiteBoardVariable.Operation.TEXT_CLICK: //文本菜单被点击
            case WhiteBoardVariable.Operation.ERASER_CLICK: //橡皮擦菜单被点击
                switch (OperationUtils.getInstance().mCurrentOPerationPen) {
                    case WhiteBoardVariable.Operation.PEN_NORMAL:
                        break;
                    case WhiteBoardVariable.Operation.PEN_CLICK:
                        mFabMenuSize.clearDraw();
                        mFabMenuSize.setAddButtonBackground(R.drawable.white_board_pen_selector);
                        OperationUtils.getInstance().mCurrentOPerationPen = WhiteBoardVariable.Operation.PEN_NORMAL;
                        break;
                    case WhiteBoardVariable.Operation.PEN_EXPAND:
                        mFabMenuSize.collapse();
                        mFabMenuSize.clearDraw();
                        mFabMenuSize.setAddButtonBackground(R.drawable.white_board_pen_selector);
                        OperationUtils.getInstance().mCurrentOPerationPen = WhiteBoardVariable.Operation.PEN_NORMAL;
                        break;
                }
                break;
            case WhiteBoardVariable.Operation.COLOR_CLICK:     //颜色菜单被点击
            case WhiteBoardVariable.Operation.OUTSIDE_CLICK:   //点击在外面
                switch (OperationUtils.getInstance().mCurrentOPerationPen) {
                    case WhiteBoardVariable.Operation.PEN_NORMAL:
                        break;
                    case WhiteBoardVariable.Operation.PEN_CLICK:
                        break;
                    case WhiteBoardVariable.Operation.PEN_EXPAND:
                        mFabMenuSize.collapse();
                        OperationUtils.getInstance().mCurrentOPerationPen = WhiteBoardVariable.Operation.PEN_CLICK;
                        break;
                }
                break;

        }

    }

    /**
     * 白板工具栏点击切换操作-颜色
     */
    private void setColorOperation(int currentOperation) {
        switch (currentOperation) {
            case WhiteBoardVariable.Operation.PEN_CLICK:
            case WhiteBoardVariable.Operation.TEXT_CLICK:
            case WhiteBoardVariable.Operation.ERASER_CLICK:
            case WhiteBoardVariable.Operation.BACKCOLOR_CLICK:
            case WhiteBoardVariable.Operation.OUTSIDE_CLICK:
                //Log.v("yang",currentOperation+"  + " +OperationUtils.getInstance().mCurrentOPerationBackColor);
                switch (OperationUtils.getInstance().mCurrentOPerationColor) {
                    case WhiteBoardVariable.Operation.COLOR_NORMAL:
                        break;
                    case WhiteBoardVariable.Operation.COLOR_EXPAND:
                        mFabMenuColor.collapse();
                        OperationUtils.getInstance().mCurrentOPerationColor = WhiteBoardVariable.Operation.COLOR_NORMAL;
                        break;
                }
                break;
            case WhiteBoardVariable.Operation.COLOR_CLICK:
                switch (OperationUtils.getInstance().mCurrentOPerationColor) {
                    case WhiteBoardVariable.Operation.COLOR_NORMAL:
                        mFabMenuColor.expand();
                        OperationUtils.getInstance().mCurrentOPerationColor = WhiteBoardVariable.Operation.COLOR_EXPAND;
                        break;
                    case WhiteBoardVariable.Operation.COLOR_EXPAND:
                        mFabMenuColor.collapse();
                        OperationUtils.getInstance().mCurrentOPerationColor = WhiteBoardVariable.Operation.COLOR_NORMAL;
                        break;
                }
                break;

        }

    }

    /**
     * 白板工具栏点击切换操作-橡皮擦
     */
    private void setEraserOperation(int currentOperation) {
        switch (currentOperation) {
            case WhiteBoardVariable.Operation.ERASER_CLICK:
                switch (OperationUtils.getInstance().mCurrentOPerationEraser) {
                    case WhiteBoardVariable.Operation.ERASER_NORMAL:
                        OperationUtils.getInstance().mCurrentDrawType = OperationUtils.DRAW_ERASER;
                        mDbView.changeEraser();
                        mFabMenuEraser.setAddButtonBackground(R.drawable.white_board_eraser_selected_selector);
                        OperationUtils.getInstance().mCurrentOPerationEraser = WhiteBoardVariable.Operation.ERASER_CLICK;
                        break;
                    case WhiteBoardVariable.Operation.ERASER_CLICK:
                        mFabMenuEraser.expand();
                        changeEraserBack();  //选中后改变菜单的背景（设置为相应的选中项）
                        OperationUtils.getInstance().mCurrentOPerationEraser = WhiteBoardVariable.Operation.ERASER_EXPAND;
                        break;
                    case WhiteBoardVariable.Operation.ERASER_EXPAND:
                        mFabMenuEraser.collapse();
                        OperationUtils.getInstance().mCurrentOPerationEraser = WhiteBoardVariable.Operation.ERASER_CLICK;
                        break;
                }
                break;
            case WhiteBoardVariable.Operation.TEXT_CLICK:
            case WhiteBoardVariable.Operation.BACKCOLOR_CLICK:
            case WhiteBoardVariable.Operation.PEN_CLICK:
                switch (OperationUtils.getInstance().mCurrentOPerationEraser) {
                    case WhiteBoardVariable.Operation.ERASER_NORMAL:
                        break;
                    case WhiteBoardVariable.Operation.ERASER_CLICK:
                        mFabMenuEraser.clearDraw();
                        mFabMenuEraser.setAddButtonBackground(R.drawable.white_board_eraser_selector);
                        OperationUtils.getInstance().mCurrentOPerationEraser = WhiteBoardVariable.Operation.ERASER_NORMAL;
                        break;
                    case WhiteBoardVariable.Operation.ERASER_EXPAND:
                        mFabMenuEraser.collapse();
                        mFabMenuEraser.clearDraw();
                        mFabMenuEraser.setAddButtonBackground(R.drawable.white_board_eraser_selector);
                        OperationUtils.getInstance().mCurrentOPerationEraser = WhiteBoardVariable.Operation.ERASER_NORMAL;
                        break;
                }
                break;
            case WhiteBoardVariable.Operation.COLOR_CLICK:
            case WhiteBoardVariable.Operation.OUTSIDE_CLICK:
                switch (OperationUtils.getInstance().mCurrentOPerationEraser) {
                    case WhiteBoardVariable.Operation.ERASER_NORMAL:
                        break;
                    case WhiteBoardVariable.Operation.ERASER_CLICK:
                        break;
                    case WhiteBoardVariable.Operation.ERASER_EXPAND:
                        mFabMenuEraser.collapse();
                        OperationUtils.getInstance().mCurrentOPerationEraser = WhiteBoardVariable.Operation.ERASER_CLICK;
                        break;
                }
                break;

        }

    }

    /**
     * 白板工具栏点击切换操作-背景颜色
     */
    private void setBackColorOperation(int currentOperation) {
        switch (currentOperation) {
            case WhiteBoardVariable.Operation.PEN_CLICK:
            case WhiteBoardVariable.Operation.COLOR_CLICK:
            case WhiteBoardVariable.Operation.TEXT_CLICK:
            case WhiteBoardVariable.Operation.ERASER_CLICK:
            case WhiteBoardVariable.Operation.OUTSIDE_CLICK:
                Log.v("yang",currentOperation+"  + " +OperationUtils.getInstance().mCurrentOPerationBackColor);
                switch (OperationUtils.getInstance().mCurrentOPerationBackColor) {
                    case WhiteBoardVariable.Operation.BACKCOLOR_NORMAL:
                        break;
                    case WhiteBoardVariable.Operation.BACKCOLOR_EXPAND:
                        mFabMenuBack.collapse();
                        OperationUtils.getInstance().mCurrentOPerationBackColor = WhiteBoardVariable.Operation.BACKCOLOR_NORMAL;
                        break;
                }
                break;
            case WhiteBoardVariable.Operation.BACKCOLOR_CLICK:
                switch (OperationUtils.getInstance().mCurrentOPerationBackColor) {
                    case WhiteBoardVariable.Operation.BACKCOLOR_NORMAL:
                        mFabMenuBack.expand();
                        OperationUtils.getInstance().mCurrentOPerationBackColor = WhiteBoardVariable.Operation.BACKCOLOR_EXPAND;
                        break;
                    case WhiteBoardVariable.Operation.BACKCOLOR_EXPAND:
                        mFabMenuBack.collapse();
                        OperationUtils.getInstance().mCurrentOPerationBackColor = WhiteBoardVariable.Operation.BACKCOLOR_NORMAL;
                        break;
                }
                break;

        }

    }


    /**
     * 重新显示白板
     */
    private void showPoints() {
        mDbView.showPoints();
        mDtView.showPoints();
        mTvWhiteBoardPage.setText("" + (OperationUtils.getInstance().mCurrentIndex + 1) + "/" + OperationUtils.getInstance().getDrawPointSize());
        showPage();
    }

    /**
     * 显示上下页是否可点击
     */
    private void showPage() {
        if (OperationUtils.getInstance().mCurrentIndex + 1 == OperationUtils.getInstance().getDrawPointSize()) {
            mIvWhiteBoardNext.setImageResource(R.drawable.white_board_next_page_click);
        } else {
            mIvWhiteBoardNext.setImageResource(R.drawable.white_board_next_page_selector);
        }
        if (OperationUtils.getInstance().mCurrentIndex == 0) {
            mIvWhiteBoardPre.setImageResource(R.drawable.white_board_pre_page_click);
        } else {
            mIvWhiteBoardPre.setImageResource(R.drawable.white_board_pre_page_selector);
        }

    }

    /**
     * 撤销
     */
    private void undo() {
        int size = OperationUtils.getInstance().getSavePoints().size();
        if (size == 0) {
            return;
        } else {
            OperationUtils.getInstance().getDeletePoints().add(OperationUtils.getInstance().getSavePoints().get(size - 1));
            OperationUtils.getInstance().getSavePoints().remove(size - 1);
            size = OperationUtils.getInstance().getDeletePoints().size();
            if (OperationUtils.getInstance().getDeletePoints().get(size - 1).getType() == OperationUtils.DRAW_PEN) {
                mDbView.undo();
            } else if (OperationUtils.getInstance().getDeletePoints().get(size - 1).getType() == OperationUtils.DRAW_TEXT) {
                mDtView.undo();
            }
        }

    }

    /**
     * 重做
     */
    private void redo() {
        int size = OperationUtils.getInstance().getDeletePoints().size();
        if (size == 0) {
            return;
        } else {
            OperationUtils.getInstance().getSavePoints().add(OperationUtils.getInstance().getDeletePoints().get(size - 1));
            OperationUtils.getInstance().getDeletePoints().remove(size - 1);
            size = OperationUtils.getInstance().getSavePoints().size();
            if (OperationUtils.getInstance().getSavePoints().get(size - 1).getType() == OperationUtils.DRAW_PEN) {
                mDbView.redo();
            } else if (OperationUtils.getInstance().getSavePoints().get(size - 1).getType() == OperationUtils.DRAW_TEXT) {
                mDtView.redo();
            }
        }

    }

    /**
     * 显示挡板
     */
    private void showOutSideView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (OperationUtils.getInstance().mCurrentOPerationPen == WhiteBoardVariable.Operation.PEN_EXPAND
                        || OperationUtils.getInstance().mCurrentOPerationColor == WhiteBoardVariable.Operation.COLOR_EXPAND
                        || OperationUtils.getInstance().mCurrentOPerationText == WhiteBoardVariable.Operation.TEXT_EXPAND
                        || OperationUtils.getInstance().mCurrentOPerationEraser == WhiteBoardVariable.Operation.ERASER_EXPAND
                        || OperationUtils.getInstance().mCurrentOPerationBackColor == WhiteBoardVariable.Operation.BACKCOLOR_EXPAND) {
                    mVBottomBack.setVisibility(View.VISIBLE);
                } else {
                    mVBottomBack.setVisibility(View.GONE);
                }
            }
        }, 100);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_white_board_back://返回键
                Intent intent = new Intent(WhiteBoardActivity.this,MainActivity.class);
                startActivity(intent);
                WhiteBoardActivity.this.finish();
                break;
            case R.id.iv_white_board_export://保存白板操作集到本地
                final View dialog_layout = getLayoutInflater().inflate(R.layout.dialog_layout,null);
                new AlertDialog.Builder(this).setTitle("请输入保存的文件名").setIcon(
                        android.R.drawable.ic_dialog_info).setView(
                        dialog_layout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = ((EditText)dialog_layout.findViewById(R.id.file_name)).getText().toString();
                        ToolsOperation(WhiteBoardVariable.Operation.OUTSIDE_CLICK);
                        StoreUtil.saveWhiteBoardPoints(name);
                    }
                }).setNegativeButton("取消", null).show();

                break;
            case R.id.iv_white_board_save://保存白板为图片
                ToolsOperation(WhiteBoardVariable.Operation.OUTSIDE_CLICK);
                saveImage();
                break;
            case R.id.v_bottom_back://点击挡板
                ToolsOperation(WhiteBoardVariable.Operation.OUTSIDE_CLICK);
                break;
            case R.id.bt_size_large://设置画笔尺寸-大号
                setPenSize(WhiteBoardVariable.PenSize.LARRGE);
                break;
            case R.id.bt_size_middle://设置画笔尺寸-中号
                setPenSize(WhiteBoardVariable.PenSize.MIDDLE);
                break;
            case R.id.bt_size_mini://设置画笔尺寸-小号
                setPenSize(WhiteBoardVariable.PenSize.MINI);
                break;

            case R.id.bt_color_green://设置颜色-绿色
                setColor(WhiteBoardVariable.Color.GREEN);
                break;
            case R.id.bt_color_purple://设置颜色-紫色
                setColor(WhiteBoardVariable.Color.PURPLE);
                break;
            case R.id.bt_color_pink://设置颜色-粉色
                setColor(WhiteBoardVariable.Color.PINK);
                break;
            case R.id.bt_color_orange://设置颜色-橙色
                setColor(WhiteBoardVariable.Color.ORANGE);
                break;
            case R.id.bt_color_black://设置颜色-黑色
                setColor(WhiteBoardVariable.Color.BLACK);
                break;
            case R.id.bt_color_pick://设置颜色-自定义
                ColorPickerDialog dialog = new ColorPickerDialog(WhiteBoardActivity.this,WhiteBoardVariable.Color.BLACK);
                dialog.show();
                dialog.setOnColorChangedListener(new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void onColorChanged(int color) {
                        setColor(color);
                    }
                });
                break;

            case R.id.bt_eraser_large://设置橡皮擦尺寸-大号
                setEraserSize(WhiteBoardVariable.EraserSize.LARRGE);
                break;
            case R.id.bt_eraser_middle://设置橡皮擦尺寸-中号
                setEraserSize(WhiteBoardVariable.EraserSize.MIDDLE);
                break;
            case R.id.bt_eraser_mini://设置橡皮擦尺寸-小号
                setEraserSize(WhiteBoardVariable.EraserSize.MINI);
                break;
            case R.id.bt_eraser_space:
                mDbView.clearImage();
                break;

            case R.id.bt_back1:
                setBackColor(AppContextUtil.getColor(R.color.white));
                break;
            case R.id.bt_back2:
                setBackColor(AppContextUtil.getColor(R.color.back1));
                break;
            case R.id.bt_back3:
                setBackColor(AppContextUtil.getColor(R.color.back2));
                break;
            case R.id.bt_back4:
                setBackColor(AppContextUtil.getColor(R.color.back3));
                break;
            case R.id.iv_white_board_undo://撤销
                ToolsOperation(WhiteBoardVariable.Operation.OUTSIDE_CLICK);
                if (OperationUtils.getInstance().DISABLE) {
                    undo();
                }
                break;
            case R.id.iv_white_board_redo://重做
                ToolsOperation(WhiteBoardVariable.Operation.OUTSIDE_CLICK);
                if (OperationUtils.getInstance().DISABLE) {
                    redo();
                }
                break;
            case R.id.ll_white_board_pre:
            case R.id.iv_white_board_pre://上一页
                ToolsOperation(WhiteBoardVariable.Operation.OUTSIDE_CLICK);
                prePage();
                break;
            case R.id.ll_white_board_next:
            case R.id.iv_white_board_next://下一页
                ToolsOperation(WhiteBoardVariable.Operation.OUTSIDE_CLICK);
                nextPage();
                break;
            case R.id.iv_white_board_add://新建白板
                ToolsOperation(WhiteBoardVariable.Operation.OUTSIDE_CLICK);
                newPage();
                break;
            case R.id.iv_white_board_disable://禁止/解禁按钮
                ToolsOperation(WhiteBoardVariable.Operation.OUTSIDE_CLICK);
                if (OperationUtils.getInstance().DISABLE) {
                    OperationUtils.getInstance().DISABLE = false;
                    mIvWhiteBoardDisable.setImageResource(R.drawable.white_board_undisable_selector);
                    mRlBottom.setVisibility(View.GONE);
                } else {
                    OperationUtils.getInstance().DISABLE = true;
                    mIvWhiteBoardDisable.setImageResource(R.drawable.white_board_disable_selector);
                    mRlBottom.setVisibility(View.VISIBLE);
                }
                break;

        }
    }

    /**
     * 新建白板
     */
    private void newPage() {
        OperationUtils.getInstance().newPage();
        showPoints();
    }

    /**
     * 上一页
     */
    private void prePage() {
        if (OperationUtils.getInstance().mCurrentIndex > 0) {
            OperationUtils.getInstance().mCurrentIndex--;
            showPoints();
        }
    }

    /**
     * 下一页
     */
    private void nextPage() {
        if (OperationUtils.getInstance().mCurrentIndex + 1 < OperationUtils.getInstance().getDrawPointSize()) {
            OperationUtils.getInstance().mCurrentIndex++;
            showPoints();
        }
    }

    /**
     * 保存当前白板为图片
     */
    public void saveImage() {
        String fileName = StoreUtil.getPhotoSavePath();
        File file = new File(fileName);
        try {
            File directory = file.getParentFile();
            if (!directory.exists() && !directory.mkdirs()) {
                Toast.makeText(WhiteBoardActivity.this,getString(R.string.white_board_export_fail),Toast.LENGTH_LONG).show();
                return;
            }
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            mFlView.setDrawingCacheEnabled(true);
            mFlView.buildDrawingCache();
            Bitmap bitmap = mFlView.getDrawingCache();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            mFlView.destroyDrawingCache();

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            sendBroadcast(intent);//这个广播的目的就是更新图库

            Toast.makeText(WhiteBoardActivity.this,getString(R.string.white_board_export_tip) + fileName,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(WhiteBoardActivity.this,getString(R.string.white_board_export_fail),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
    /**
     * 设置颜色
     */
    private void setColor(int color) {
        OperationUtils.getInstance().mCurrentColor = color;
        changeColorBack();
        setPenSize(OperationUtils.getInstance().mCurrentPenSize);
        mDbView.setPenColor();
        mDtView.setTextColor();
    }

    private void setBackColor(int color){
        OperationUtils.getInstance().mCurrentBackColor = color;
       // changeDrawBack();
        setPenSize(OperationUtils.getInstance().mCurrentPenSize);
        mDbView.setBackgroundColor(color);

    }
    /**
     * 设置画笔尺寸
     */
    private void setPenSize(int size) {
        OperationUtils.getInstance().mCurrentPenSize = size;
        changePenBack();
        mDbView.setPenSize();
    }

    /**
     * 设置橡皮擦尺寸
     */
    private void setEraserSize(int size) {
        OperationUtils.getInstance().mCurrentEraserSize = size;
        changeEraserBack();
        mDbView.setEraserSize();

    }


}