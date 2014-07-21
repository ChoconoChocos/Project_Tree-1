package tree;

import java.awt.event.MouseEvent;
import java.awt.Point;
import mvc.Controller;

/**
 * 樹状整列におけるコントローラ。
 */
public class TreeController extends Controller
{
    /**
     * インスタンスを生成する。
     */
    public TreeController()
    {
        super();
    }
    
    /**
	 * ダブルバッファのpicture画像を基に、木全体を全体を動かす。
     * @param aMouseEvent マウスイベントのインスタンス
	 */
    public void mouseDragged(MouseEvent aMouseEvent)
	{
		super.mouseDragged(aMouseEvent);
		return;
	}
    
    /**
	 * マウスクリックした位置の木の要素（文字列）を出力する。
     * @param aMouseEvent マウスイベントのインスタンス
	 */
	public void mouseClicked(MouseEvent aMouseEvent)
	{
        Point aPoint = aMouseEvent.getPoint();
		aPoint.translate(view.scrollAmount().x, view.scrollAmount().y);
		TreeModel aTreeModel = (TreeModel)(this.model);
		aTreeModel.mouseClicked(aPoint, aMouseEvent);
		return;
	}
}