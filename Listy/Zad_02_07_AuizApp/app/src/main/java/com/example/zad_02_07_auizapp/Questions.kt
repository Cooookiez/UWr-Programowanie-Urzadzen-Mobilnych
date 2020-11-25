package com.example.zad_02_07_auizapp

object Questions {
    fun getQuestions(): ArrayList<Question> {
        val questionList = ArrayList<Question>()

        val question01 = Question(
            "Question one",
            R.drawable.ic_kiritimati_flag,
            "Ans 1",
            "Ans 2",
            "Ans 3",
            1
        )
        val question02 = Question(
            "Question two",
            R.drawable.ic_kiritimati_flag,
            "Ans 1",
            "Ans 2",
            "Ans 3",
            2
        )
        val question03 = Question(
            "Question three",
            R.drawable.ic_kiritimati_flag,
            "Ans 1",
            "Ans 2",
            "Ans 3",
            3
        )
        val question04 = Question(
            "Question four",
            R.drawable.ic_kiritimati_flag,
            "Ans 1",
            "Ans 2",
            "Ans 3",
            1
        )
        val question05 = Question(
            "Question five",
            R.drawable.ic_kiritimati_flag,
            "Ans 1",
            "Ans 2",
            "Ans 3",
            2
        )
        val question06 = Question(
            "Question six",
            R.drawable.ic_kiritimati_flag,
            "Ans 1",
            "Ans 2",
            "Ans 3",
            3
        )
        val question07 = Question(
            "Question seven",
            R.drawable.ic_kiritimati_flag,
            "Ans 1",
            "Ans 2",
            "Ans 3",
            1
        )
        val question08 = Question(
            "Question eight",
            R.drawable.ic_kiritimati_flag,
            "Ans 1",
            "Ans 2",
            "Ans 3",
            2
        )
        val question09 = Question(
            "Question nine",
            R.drawable.ic_kiritimati_flag,
            "Ans 1",
            "Ans 2",
            "Ans 3",
            3
        )
        val question10 = Question(
            "Question ten",
            R.drawable.ic_kiritimati_flag,
            "Ans 1",
            "Ans 2",
            "Ans 3",
            1
        )

        questionList.add(question01)
        questionList.add(question02)
        questionList.add(question03)
        questionList.add(question04)
        questionList.add(question05)
        questionList.add(question06)
        questionList.add(question07)
        questionList.add(question08)
        questionList.add(question09)
        questionList.add(question10)

        return questionList
    }
}