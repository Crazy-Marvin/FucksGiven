import 'package:fucksgiven/model/model.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';

import 'package:intl/intl.dart';

class NotesDatabase {
  static final NotesDatabase instance =NotesDatabase._init();

  static Database? _database;
  NotesDatabase._init();

  Future<Database> get database async{
    // if database exists, we return the database
    if(_database!=null) return  _database!;

    //initialize the database
    //_initDB method is called
    _database = await _initDB('notes.db');
    return _database!;

  }

  Future<Database> _initDB(String filePath) async{
    final dbPath = await getDatabasesPath();
    final path = join(dbPath,filePath);

    return await openDatabase(path,version: 1, onCreate: _createDB);


  }

  Future _createDB(Database db, int version) async{
    final idType = "INTEGER PRIMARY KEY AUTOINCREMENT";
    final textType = "TEXT NOT NULL";

  await db.execute('''
  CREATE TABLE $tableNotes (
  ${NotesField.id} $idType,
  ${NotesField.title} $textType,
  ${NotesField.time} $textType
  )
  ''');
  }
  Future<Note> create(Note note) async{

    final db = await instance.database;
    final id = await db.insert(tableNotes, note.toJson());

    return note.copy(id : id);
  }

  Future<Note> readNote(int id) async{
    final db = await instance.database;
    final maps = await db.query(
    tableNotes,
      columns: NotesField.values,
      where: '${NotesField.id} = ?',
      whereArgs: [id],
    );
    if(maps.isNotEmpty){
      return Note.fromJson(maps.first);
    }
    else{
      throw Exception('id $id not found');
    }
  }
  readNotebyDate(String date) async{
    final db = await instance.database;
    final maps = await db.query(
      tableNotes,
      columns: NotesField.values,
      where: '${NotesField.time} = ?',
      whereArgs: [date],
    );
    if(maps.isNotEmpty){
      print(maps);
      return maps;
    }
    else{
      throw Exception(' not found');
    }
  }
  Future<int> notesByDateCount(String date) async{
    final db = await instance.database;
    final maps = await db.query(
      tableNotes,
      columns: NotesField.values,
      where: '${NotesField.time} = ?',
      whereArgs: [date],
    );
    if(maps.isNotEmpty){
      print(maps.length);
      return maps.length;
    }
    else{
      throw Exception(' not found');
    }
  }


  Future<List<Note>> readAllNotes() async{
    final db = await instance.database;
    final orderBy = '${NotesField.time} ASC';
    final List<Map<String, dynamic>> maps= await db.query(tableNotes,orderBy: orderBy);

    return List.generate(maps.length, (i) {
      return Note(
        title: maps[i]['title'],
        createdTime: maps[i]['time'],

      );
    });
  }
  Future<List<Note>> readDates() async{
    final db = await instance.database;
    final orderBy = '${NotesField.time} ASC';
    final List<Map<String, dynamic>> maps = await db.query(tableNotes,orderBy: orderBy);
    return List.generate(maps.length, (i) {
      return Note(
         title: maps[i]['title'],
        createdTime: maps[i]['createdTime'],
      );
    });
    // return result.map((json) => Note.fromJson(json)).toList();
  }
  Future deleteAll() async{
    final db = await instance.database;
    return await db.rawDelete("Delete from $tableNotes");
    // return result.map((json) => Note.fromJson(json)).toList();
  }
  //close the database function
  Future close() async{
    final db = await instance.database;
    db.close();
  }


  Future<List<Note>> messageList(String date) async {
    final db = await instance.database;
    final List<Map<String, dynamic>> maps =
    await db.query(
      tableNotes,
      columns: NotesField.values,
      where: '${NotesField.time} = ?',
      whereArgs: [date],
    );
    return List.generate(maps.length, (i) {
      return Note(
        title: maps[i]['title'],
        createdTime:maps[i]['createdTime'],

      );
    }
    );
  }

}
