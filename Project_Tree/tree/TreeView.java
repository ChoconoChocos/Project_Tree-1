package tree;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.NullPointerException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import mvc.View;

/**
 * 樹状整列におけるビュー
 */
public class TreeView extends View
{
    /**
     * 情報操作をするTreeModelのインスタンスを束縛する。
     */
    protected TreeModel aTreeModel;
    
    /**
     * 制御を司るTreeControllerのインスタンスを束縛する。
     */
    protected TreeController aTreeController;
    
    /**
     * インスタンスを生成して応答する。
     * 指定されたモデルの依存物となり、指定されたコントローラにモデルとビューを設定し、スクロール量をを(0, 0)に設定する。
     * @param aTreeModel 指定されたツリーモデル
     * @param aTreeController 指定されたツリーコントローラ
     */
    public TreeView(TreeModel aTreeModel, TreeController aTreeController)
    {
        super(aTreeModel, aTreeController);
        this.model = aTreeModel;
        this.controller = aTreeController;
    }
    
    private static int index = -1;
    /**
     * 指定されたグラフィクスに背景色（白色）でビュー全体を塗り、その上ににモデルの内容物を描画する。
     * スクロール量に応じてモデル画像(picture)をパネル内に描画する。
     * @param baseGraphics 指定されたグラフィックス
     */
    public void paintComponent(Graphics baseGraphics)
    {
        this.setSize(3500, 3500);
        int width = this.getWidth();
        int height = this.getHeight();
        
        baseGraphics.setColor(Color.white);
        baseGraphics.fillRect(0, 0, width, height);
        BufferedImage picture = (BufferedImage)this.createImage(width, height);
        Graphics aGraphics = picture.getGraphics();
        aGraphics.setColor(Color.white);
        aGraphics.fillRect(0, 0, width, height);
        aGraphics.setColor(Color.black);
        
        aTreeModel = (TreeModel)this.model;
        aTreeController = (TreeController)this.controller;
        ArrayList<Element> elementList = aTreeModel.getElementSortList();
        if(index >= 0 && index < elementList.size())
        {
            elementList.get(index).setPosition(elementList.get(index).getAfterPosition());
        }
        index++;
        
        //繋ぎ線
        for(Element aElement : aTreeModel.getElementSortList())
        {
            if(aElement.getParents().size() != 0)
            {
                Point Parents = aTreeModel.getElementList().get(aElement.getParents().get(0) - 1).getPosition();
                Point Children = aElement.getPosition();
                Dimension ParentsDimension = aTreeModel.getElementList().get(aElement.getParents().get(0) - 1).getSize();
                Dimension ChildrenDimension = aTreeModel.getElementList().get(aElement.getParents().get(0) - 1).getSize();
                
                aGraphics.drawLine(Parents.x + ParentsDimension.width + Constants.RECT_BLANKSPASE,
                                   Parents.y - ParentsDimension.height/2, Children.x, Children.y - ChildrenDimension.height/2);
            }
        }
        
        for(Element aElement : elementList)
        {
            Dimension aDimension = aElement.getSize();
            
            aGraphics.setFont(aElement.getFont());
            aGraphics.drawString(aElement.getNodeName(),
                                 aElement.getPosition().x + Constants.RECT_BLANKSPASE,
                                 aElement.getPosition().y - (Constants.DISPLACEMENT - Constants.FONT_SIZE - Constants.HEIDHT_SPASE));
            aGraphics.drawRect(aElement.getPosition().x,
                               aElement.getPosition().y - Constants.DISPLACEMENT,
                               aDimension.width + Constants.RECT_BLANKSPASE,
                               aDimension.height);
        }
        Point aPoint = this.scrollAmount();
        baseGraphics.drawImage(picture, 0-aPoint.x, 0-aPoint.y, this);
    
        try
        {
            repaint();
            Thread.sleep(100);
        }
        catch(InterruptedException aInterruptedException)
        {
            System.out.println(aInterruptedException);
        }
        return;
    }
    
    /**
     * 読み込むファイルを指定させるウィンドウを表示し、指定されたファイルパスを文字列を返す。
     * @return 読み込んだファイルのパスの文字列。
     * バグ：JFileChooser.showOpenDialogの中で選択ダイアログが表示されず処理が止まる。
     */
    public String fileSelectFrame()
    {
        JFrame aFrame = new JFrame();
        File aFile = null;
        String aFilePath = null;
        JFileChooser filechooser = new JFileChooser();
        
        int selected=0;
        try
        {
            selected = filechooser.showOpenDialog(null);
        }
        catch(HeadlessException aHeadlessException)
        {
            System.out.println(aHeadlessException);
        }
            
        if(selected == JFileChooser.APPROVE_OPTION)
        {
            aFile = filechooser.getSelectedFile();
            System.out.println("ファイルが選択されました = " + aFile.getName());
        }
        else if(selected == JFileChooser.CANCEL_OPTION)
        {
            System.out.println("キャンセルされました。");
        }
        else if (selected == JFileChooser.ERROR_OPTION)
        {
            System.out.println("エラー又は取り消しがありました。");
        }
        
        try
        {
            aFilePath = aFile.getAbsolutePath();
        }
        catch(NullPointerException aNullPointerException)
        {
        	System.out.println(aNullPointerException);
        	System.exit(0);
        }
        return aFilePath;
    }
}