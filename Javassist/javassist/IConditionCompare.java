package yan.study.javassist;

import java.util.Map;

/**
 * 条件比较接口
 * @author yanjunhao
 *
 */
public interface IConditionCompare {
	public boolean compare(Map<String,Object> condictionMap);
}
