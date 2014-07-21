package tree;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import mvc.Model;

/**
 * 樹状整列におけるモデル
 */
public class TreeModel extends Model
{
    /**
     * ノード(Element)をリストで束縛する。
     */
    private static ArrayList<Element> elementList;
    
    /**
     * ノード(Element)をリストで束縛する（ソートしたもの）。
     */
    private ArrayList<Element> sortElementList = new ArrayList<Element>();
    
    /**
     * y座標を設定するための値。
     */
    private static int indexSort = Constants.DISPLACEMENT;
    
    /**
     * y座標を設定するための値。
     */
    private static int indexSortParent = Constants.DISPLACEMENT;
    
	/**
	 * インスタンスを生成して初期化して応答する。
     * またテキストファイルを読み込み、樹の要素をエレメントに設定し、描画する順にソートする。
     * @param aFilePath 指定されたファイルパス。
	 */
	public TreeModel()
	{
		super();
        TreeView aTreeView = new TreeView(this, new TreeController());
        this.setElement(aTreeView.fileSelectFrame());
        this.sort();
	}
    
    /**
     * 指定されたファイルパスを読み込み、要素を設定する。
     * @param aFilePath 指定されたファイルパス。
     */
    public void setElement(String aFilePath)
    {
        FileReader aFileReader = null;
        BufferedReader aBufferedReader = null;
        elementList = new ArrayList<Element>();
        ArrayList<String> treesList = new ArrayList<String>();
        ArrayList<String> nodesList = new ArrayList<String>();
        ArrayList<String> branchesList = new ArrayList<String>();
        String aLine = null;
        try
        {
            aFileReader = new FileReader(aFilePath);
        }
        catch(FileNotFoundException aFileNotFoundException)
        {
            aFileNotFoundException.printStackTrace();
        }
        aBufferedReader = new BufferedReader(aFileReader);
        try
        {
            aLine = aBufferedReader.readLine();
        }
        catch(IOException anIOException)
        {
            anIOException.printStackTrace();
        }
        while(!aLine.equals("nodes:"))
        {
            try
            {
                aLine = aBufferedReader.readLine();
            }
            catch(IOException anIOException)
            {
                anIOException.printStackTrace();
            }
            if(!aLine.equals("nodes:"))
            {
                treesList.add(aLine);
            }
        }
        while(!aLine.equals("branches:"))
        {
            try
            {
                aLine = aBufferedReader.readLine();
            }
            catch(IOException anIOException)
            {
                anIOException.printStackTrace();
            }
            if(!aLine.equals("branches:"))
            {
                nodesList.add(aLine);
            }
        }
        while(aLine != null)
        {
            try
            {
                aLine = aBufferedReader.readLine();
            }
            catch(IOException anIOException)
            {
                anIOException.printStackTrace();
            }
            if(aLine != null)
            {
                branchesList.add(aLine);
            }
        }
        for(String aString : nodesList)
        {
            String[] string = aString.split(", ");
            getElementList().add(new Element(Integer.parseInt(string[0]), string[1]));
        }
        for(String aString : branchesList)
        {
            String[] string = aString.split(", ");
            getElementList().get(Integer.parseInt(string[0]) - 1).setChildren(Integer.parseInt(string[1]));
            getElementList().get(Integer.parseInt(string[1]) - 1).setParents(Integer.parseInt(string[0]));
        }
    }
    
    /**
     * 子をアルファベット順にソートしたのち描画する順にソートしアレイリストに設定する。
     */
    public void sort()
    {
        for(Element aElement : getElementList())
        {
            if(aElement.getChildren().size() > 0){
                ArrayList<String> nodeNameList = new ArrayList<String>();
                ArrayList<Integer> childrenList = new ArrayList<Integer>();
                for(int index : aElement.getChildren())
                {
                    nodeNameList.add(getElementList().get(index - 1).getNodeName());
                }
                Collections.sort(nodeNameList);
                for(String aString : nodeNameList)
                {
                    for(Element element : getElementList())
                    {
                        if(element.getNodeName() == aString)
                        {
                            childrenList.add(element.getNodeNumber());
                        }
                    }
                }
                aElement.resetChildren(childrenList);
            }
        }
        for(Element aElement : getElementList())
        {
            Dimension aDimension = aElement.getSize();
            aElement.setWidth(aDimension.width);
            aElement.setHeight(aDimension.height);
        }
        for(Element aElement : getElementList())
        {
            if(aElement.getParents().size() == 0)
            {
                searchChild(aElement);
            }
        }
    }
    
    /**
     * 指定された要素から再帰的に子をたどっていき、アレイリストに格納する。
     * またノードを描くx座標とy座標を設定する。
     * @param aElement 指定された要素。
     */
    public void searchChild(Element aElement)
    {
        Point aPoint;
        if(aElement.getParents().size() == 0)
        {
            indexSortParent += Constants.DISPLACEMENT * 20;
            aPoint = new Point(0, indexSortParent);
        }
        else
        {
            aPoint = new Point(getElementList().get(aElement.getParents().get(0) - 1).getAfterPosition().x + getElementList().get(aElement.getParents().get(0) - 1).getWidth() + 25, indexSort);
        }
        
        aElement.setAfterPosition(aPoint);
        indexSort += aElement.getHeight() + 4;
        sortElementList.add(aElement);
        if(aElement.getChildren().size() == 0)
        {
            return;
        }
        else
        {
            int index = 0;
            while(index < aElement.getChildren().size())
            {
                searchChild(getElementList().get((aElement.getChildren().get(index)) - 1));
                index++;
            }
        }
    }
    
    /**
	 * マウスクリックした位置を座標取得し、その座標範囲に要素があれば出力
     * @param aPoint ピクチャ座標
     * @param aMouseEvent マウスのイベント
	 */
	public void mouseClicked(Point aPoint, MouseEvent aMouseEvent)
	{
        ArrayList<Element> element = this.getElementList();
        for (Element aElement : element) {
            if(aElement.getAfterPosition().x <= aPoint.x && aElement.getAfterPosition().x + aElement.getWidth() >= aPoint.x)
            {
                if(aElement.getAfterPosition().y >= aPoint.y && aElement.getAfterPosition().y - aElement.getHeight() <= aPoint.y)
                {
                    System.out.println(aElement.getNodeName());
                }
            }
        }
		return;
	}
    
    /**
     * ノードのリストを返す。
     * @return ノードのリスト。
     */
    public ArrayList<Element> getElementList()
    {
        return elementList;
    }
    
    /**
     * ノードをソートしたリストを返す。
     * @return ノードのソートリスト。
     */
    public ArrayList<Element> getElementSortList()
    {
        return sortElementList;
    }
}