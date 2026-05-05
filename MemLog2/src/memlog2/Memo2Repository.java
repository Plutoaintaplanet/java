package memlog2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Memo2Repository {
    private static Map<Integer, Memo2> memos = new HashMap<>();
    private static int nextId = 1;

    public void saveMemo(Memo2 memo) {
        if (memo.getId() == 0) {
            memo.setId(nextId++);
        }
        memos.put(memo.getId(), memo);
    }

    public List<Memo2> getAllMemos() {
        return new ArrayList<>(memos.values());
    }

    public void deleteMemo(int id) {
        memos.remove(id);
    }
}
