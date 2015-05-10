package com.soften.ltp.judger;

import com.alibaba.fastjson.JSONObject;
import com.soften.ltp.TransposeJson.Sent;
import com.soften.ltp.TransposeJson.Word;

import java.util.List;

/**
 * Created by mysgq1 on 15/3/5.
 */
public abstract class Judger {
    public abstract JudgeResult emotionJudge(List<Word> wordList);
}
