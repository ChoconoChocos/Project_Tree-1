package tree;


import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;

/**
 * 樹状整列における要素(Node)
 */
public class Element extends JLabel
{
    /**
     * ノードの親を格納するフィールド
     */
    private ArrayList<Integer> parents;
    
    /**
     * ノードの子を格納するフィールド
     */
    private ArrayList<Integer> children;
	
    /**
     * 自身の番号を束縛する。
     */
	private int aNodeNumber;
    
    /**
     * 自身のノードの名前を束縛する。
     */
	private String aNodeName;
    
    /**
     * 描画位置を束縛する。
     */
    private Point aPosition;
    
    /**
     * 移動後の描画位置を束縛する。
     */
    private Point afterPosition;
    
	/**
	 * 自身の高さを束縛する。
	 */
	private int aHeight;
	
	/**
	 * 自身の幅を束縛する。
	 */
	private int aWidth;
    
    /**
     * 変位させる座標を文字列のy座標変位に設定し、束縛する。
     */
    private static int index = Constants.DISPLACEMENT;
	
	/**
     * 指定されたノード番号、ノードの名前と描画する位置を設定し、描画における詳細設定をする。
     * @param number 指定されたノード番号
     * @param name 指定されたノードの名前
     */
	public Element(int number, String name)
	{
		this.aNodeNumber = number;
		this.aNodeName = name;
        this.parents = new ArrayList<Integer>();
        this.children = new ArrayList<Integer>();
        this.setFont(new Font("Arial", Font.PLAIN, Constants.FONT_SIZE));
        this.setText(this.aNodeName);
		this.setBorder(new LineBorder(Color.black, 1));
        this.setSize(this.getPreferredSize());
        Point aPoint = new Point(0, index);
        this.setPosition(aPoint);
        index += 20;
	}
	
    /**
     * 指定された親のノード番号をリストに格納する。
     * @param parents 親のノード番号。
     */
    public void setParents(int parents)
    {
        this.parents.add(parents);
    }
    
    /**
     * 自分の親をアレイリストで返す。
     * @return 自分の親のアレイリスト。
     */
    public ArrayList<Integer> getParents()
    {
        return this.parents;
    }
    
    /**
     * 指定された子のノード番号をリストに格納する。
     * @param child 子のノード番号。
     */
    public void setChildren(int child)
    {
        this.children.add(child);
    }
    
    /**
     * 指定された子のリストを格納する。
     * @param aList 指定された子のリスト。
     */
    public void resetChildren(ArrayList<Integer> aList)
    {
        this.children = aList;
    }
    
    /**
     * 自分の子をアレイリストで返す。
     * @return 自分の子のアレイリスト。
     */
    public ArrayList<Integer> getChildren()
    {
        return this.children;
    }
    
    /**
     * 自分のノード番号を返す。
     * @return 自分のノード番号。
     */
	public int getNodeNumber()
    {
        return this.aNodeNumber;
    }
    
	/**
     * 指定された高さに設定する。
     * @param aNumber 指定された高さ。
     */
	public void setHeight(int aNumber)
    {
        this.aHeight = aNumber;
    }
	
	/**
     * 自分の高さを返す。
     * @return 自分の高さ。
     */
	public int getHeight()
    {
        return this.aHeight;
    }
	
	/**
     * 指定された幅に設定する。
     * @param aNumber 指定された子要素のノード番号。
     */
	public void setWidth(int aNumber)
    {
        this.aWidth = aNumber;
    }
	
    /**
     * 自分の幅を返す。
     * @return 自分の幅。
     */
	public int getWidth()
    {
        return this.aWidth;
    }
	
    /**
     * 自分のノードの名前を返す。
     * @return 自分のノードの名前。
     */
	public String getNodeName()
    {
        return this.aNodeName;
    }
    
    /**
     * 指定されたポイント（座標）に描画位置（初期位置）を設定する。
     * @param aPoint 指定された座標。
     */
    public void setPosition(Point aPoint)
    {
        this.aPosition = aPoint;
    }
    
    /**
     * 描画位置（初期位置）をポイント（座標）で返す。
     * @return 描画位置のポイント（座標）。
     */
    public Point getPosition()
    {
        return this.aPosition;
    }
    
    /**
     * 指定されたポイント（座標）に描画位置（移動後の位置）を設定する。
     * @param aPoint 指定された座標。
     */
    public void setAfterPosition(Point aPoint)
    {
        this.afterPosition = aPoint;
    }
    
    /**
     * 描画位置（移動後の位置）をポイント（座標）で返す。
     * @return 描画位置のポイント（座標）。
     */
    public Point getAfterPosition()
    {
        return this.afterPosition;
    }
}