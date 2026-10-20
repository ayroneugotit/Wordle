package com.morphense.learningapp

data class Word(val word: String, val translation: String)

class Question(val word: Word, val options: List<Word>) {
    init {
        require(options.contains(word)) {
            "The correct word must be included in the options"
        }
    }

    fun check(translation: String): Boolean {
        return word.translation == translation
    }
}

class Dictionary {
    val words = listOf(
        Word("Galaxy", "Галактика"),
        Word("House", "Дом"),
        Word("Room", "Комната"),
        Word("Door", "Дверь"),
        Word("Window", "Окно"),
        Word("Table", "Стол"),
        Word("Chair", "Стул"),
        Word("Book", "Книга"),
        Word("Pen", "Ручка"),
        Word("Phone", "Телефон"),
        Word("Computer", "Компьютер"),
        Word("Car", "Машина"),
        Word("Road", "Дорога"),
        Word("City", "Город"),
        Word("Country", "Страна"),
        Word("World", "Мир"),
        Word("Friend", "Друг"),
        Word("Family", "Семья"),
        Word("Mother", "Мать"),
        Word("Father", "Отец"),
        Word("Brother", "Брат"),
        Word("Sister", "Сестра"),
        Word("Child", "Ребёнок"),
        Word("Teacher", "Учитель"),
        Word("Student", "Студент"),
        Word("Doctor", "Врач"),
        Word("Food", "Еда"),
        Word("Bread", "Хлеб"),
        Word("Apple", "Яблоко"),
        Word("Water", "Вода"),
        Word("Coffee", "Кофе"),
        Word("Morning", "Утро"),
        Word("Evening", "Вечер"),
        Word("Night", "Ночь"),
        Word("Day", "День"),
        Word("Time", "Время"),
        Word("Music", "Музыка"),
        Word("Movie", "Фильм"),
        Word("Picture", "Картина"),
        Word("Game", "Игра"),
        Word("Question", "Вопрос"),
        Word("Answer", "Ответ"),
        Word("Idea", "Идея"),
        Word("Story", "История"),
        Word("Problem", "Проблема"),
        Word("Place", "Место"),
        Word("Beach", "Пляж"),
        Word("Mountain", "Гора"),
        Word("River", "Река"),
        Word("Tree", "Дерево")
    )

    fun generateQuestion(): Question {
        val options = List(4) { words.random() }
        val word = options.random()
        return Question(word, options)
    }

    fun generateQuestion(quantity: Int): List<Question> {
        return List(quantity) { generateQuestion() }
    }
}