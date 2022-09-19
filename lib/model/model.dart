final String tableNotes = 'notes';

class NotesField{
  static final List<String> values = [
  id,title, time,
  ];
  static final String id = '_id';
  static final String title = 'title';
  static final String time = 'time';
}

class Note{
  final int? id;
  final String title;
  final DateTime createdTime;

  const Note({
   this.id,
   required this.title,
   required this.createdTime
});

  Note copy({
    int? id,
    String? title,
    DateTime? createdTime
  }) =>
  Note(
      id:id ?? this.id,
      title:title ?? this.title,
      createdTime:createdTime ?? this.createdTime
  );


  static Note fromJson(Map<String,Object?> json) => Note(
    id: json[NotesField.id] as int,
    title: json[NotesField.title] as String,
    createdTime: DateTime.parse(json[NotesField.time] as String),


  );
  Map<String,Object?> toJson() =>{
    NotesField.id : id,
    NotesField.title :title,
    NotesField.time: createdTime.toIso8601String(),

  };



}