# Restaurant Manager

A restaurant menu digitalisation system: the owner edits the menu in a desktop
application, the menu is published as an XML file plus images on a shared FTP
folder for the table devices, and the kitchen follows incoming orders on a web
page.

Built in 2019 as a university project.

## Components

| Component | Folder | Stack |
| --- | --- | --- |
| Menu Editor | `MenuEditor/` | Java 8, JavaFX, JFoenix, AnimateFX, EclipseLink JPA, Simple XML |
| Command Viewer | `CommandViewer/` | Python 3, CherryPy, Django templates |
| Database schema | `schema.sql` | MySQL 5.7+ |

### Menu Editor

A JavaFX desktop app. On first launch it asks the owner to create an account
with the restaurant name and two brand colours; afterwards it shows a login
screen. Once inside, two screens are available:

- **Categories**: create, rename, describe and illustrate menu categories
  (first course, second course, dessert...). Images are picked with a native
  file chooser or drag and drop and copied into the shared images folder.
- **Dishes**: create dishes inside a category with description, price, weight,
  nutrition facts (energy, fat, saturated fat, carbohydrates, sugars, proteins,
  salt) and the EU allergens they contain, each shown with its icon.

The **Generate menu** button serialises the whole menu to `menu.xml` in the
shared folder. `MenuEditor/src/Resources/menu.xml` is a sample of the output:

```xml
<menu r-name="Sample Diner" info-1="#288d1ff" info-2="#000000">
   <category name="Starters">
      <imageURL>ftp/restaurant/images/CategoriesImages/starters.png</imageURL>
      <dish id="7">
         <name>House salad</name>
         <description>...</description>
         <price currency="€">8.5</price>
         <allergens>7</allergens>
         <composition measure="g/100g">
            <energy units="Kcal">120.0</energy>
            <fat>3.0</fat>
            ...
         </composition>
         <weight unit="g">250.0</weight>
      </dish>
   </category>
</menu>
```

The `datatype` package also models commands (orders), payment info and
discounts (percentage, total, per dish, multiple, extra) for the ordering side
of the system.

### Command Viewer

Ordering devices drop one XML file per command (table order) into the shared
folder. `CommandViewer/web_server.py` serves a page listing every open command
with its dishes; the kitchen presses **Completed** on a dish to remove it, and
the command file is deleted when nothing is left. See
`CommandViewer/README.md`.

### Database

`schema.sql` creates two schemas:

- `login_db.LoginData`: the owner account (username, password, restaurant
  name, primary and secondary colour).
- `menu`: `categories`, `dishes`, `Allergens` (seeded with the 14 EU
  allergens and their icon file names), and the join tables `DiCa`
  (dish to category) and `DiAl` (dish to allergen).

## Build and run

Requirements:

- MySQL 5.7 or later.
- A JDK 8 that bundles JavaFX (for example Azul Zulu 8 FX or BellSoft Liberica
  8 Full). The code targets Java 8 and uses JFoenix 8, which needs JavaFX 8.
- Maven 3.
- Python 3 for the Command Viewer.

1. Load the schema and set the database password:

   ```sh
   mysql -u root -p < schema.sql
   ```

   Edit `MenuEditor/src/META-INF/persistence.xml` and replace `CHANGE_ME`
   with your MySQL root password (or change the user).

2. Create the shared folder the devices read from. The default root is
   `/srv/ftp/restaurant`; override it with `-Dmenu.ftp.root=...` or the
   `MENU_FTP_ROOT` environment variable.

   ```
   /srv/ftp/restaurant/
     images/CategoriesImages/
     images/DishesImages/
     menu/            <- menu.xml is written here
     commands/        <- order XML files are dropped here
   ```

3. Build and run the Menu Editor:

   ```sh
   cd MenuEditor
   mvn package
   java -Dmenu.ftp.root=/srv/ftp/restaurant -jar target/menu-editor-1.0.jar
   ```

   `mvn package` copies the dependencies into `target/lib`, and the jar
   manifest references them there.

4. Run the Command Viewer:

   ```sh
   cd CommandViewer
   pip3 install CherryPy Django
   COMMANDS_DIR=/srv/ftp/restaurant/commands/ python3 web_server.py
   ```

## Layout

```
MenuEditor/
  pom.xml
  src/
    Main.java                 entry point, loads the splash screen
    splashscreen/, login/, signup/   onboarding screens (FXML + controllers)
    editor/                   categories and dishes screens, JPA entities, EditorBusiness
    datatype/                 XML model (menu, categories, dishes, commands, discounts)
    utils/XMLParseUtils.java  menu.xml writer (Simple XML)
    flow/Transition.java      scene transitions
    META-INF/persistence.xml  JPA units for login_db and menu
    Resources/                icons, allergen icons (AWIcons), sample images and menu.xml
CommandViewer/
  web_server.py, build_index.py, template.html, resources/logo.png
schema.sql
```

## License

MIT, see `LICENSE`.
