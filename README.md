# 💰 Budget Management App – v2.1

## ▶ طريقة التشغيل الوحيدة المطلوبة

```bash
cd BudgetManagementApp
mvn javafx:run
```

Maven بيحمّل JavaFX أوتوماتيك – مش محتاج أي إعداد تاني.

---

## VS Code – طريقة الـ Maven Sidebar

1. افتح الفولدر في VS Code
2. من الشريط الجانبي → أيقونة **M** (Maven)
3. `BudgetManagementApp` → `Plugins` → `javafx` → دوس مرتين على **`javafx:run`**

---

## 🔴 سبب الـ Error القديم

```
NoClassDefFoundError: Stage
```

JavaFX مش جزء من Java العادية منذ Java 11.  
الحل: Maven في `pom.xml` بيحمّلها تلقائياً.

---

## ✅ التحديثات في v2.1

| الجديد | التفاصيل |
|--------|----------|
| **NavigationService** | كلاس منفصل للتنقل (SOLID – Single Responsibility) |
| **Back / Forward** | أزرار رجوع وتقدم في شريط أعلى الشاشة |
| **BudgetService ArrayList** | الـ budgets بتتخزن في `ArrayList` في الذاكرة + ملف |
| **JDK 25 compatible** | `pom.xml` يشتغل مع JDK 17 و 21 و 25 |
| **ikonli** | مكتبة أيقونات FontAwesome جاهزة للاستخدام |

---

## هيكل المشروع

```
BudgetManagementApp/
├── pom.xml                          ← Maven config (JDK 25 safe)
├── data/
│   ├── users.txt
│   ├── transactions.txt
│   ├── budgets.txt
│   └── goals.txt
└── src/main/java/app/
    ├── Main.java
    ├── ui/
    │   ├── AppStyles.java           ← كل الألوان والـ CSS
    │   ├── NavigationService.java   ← SOLID: التنقل هنا بس
    │   ├── LoginScreen.java
    │   ├── MainScreen.java          ← Sidebar + Back/Forward
    │   ├── DashboardScreen.java
    │   ├── TransactionScreen.java
    │   ├── BudgetScreen.java
    │   ├── GoalScreen.java
    │   └── ReportScreen.java
    ├── controllers/
    ├── services/                    ← BudgetService: ArrayList + file
    ├── models/
    └── data/
```
