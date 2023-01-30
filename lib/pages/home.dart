import 'package:flutter/material.dart';
import 'package:fucksgiven/db/Notes_database.dart';
import 'package:hexcolor/hexcolor.dart';
import '../model/model.dart';
import 'package:intl/intl.dart';

class Home extends StatefulWidget {
  const Home({Key? key}) : super(key: key);

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  late List<Note> notes;
  bool isLoading = false;
  final myController = TextEditingController();
  var notesDates =[] ;
  @override
  void initState() {
    refreshNotes();
  }
  Future deleteAll() async{
    await NotesDatabase.instance.deleteAll();
  }
  Future refreshNotes() async{
    print('loadinggg........');
    // setState(() { isLoading = true;});
    this.notes = await NotesDatabase.instance.readAllNotes();

    for(int i = 0;i<this.notes.length;i++){
      notesDates.insert(0,notes[i].createdTime);

    }

    setState(() {
      notesDates= notesDates.toSet().toList();
    });
    print(notesDates.length);
    // setState(() {
    //   isLoading = false;
    // });
  }

  Future addNote(title) async{
    DateFormat dateFormat = DateFormat.yMMMMd('en_US') ;

    final note = Note(title: title, createdTime: dateFormat.format(DateTime.now()));

    await NotesDatabase.instance.create(note);
  }

  createDialogue(){
  return showDialog<void>(
    context: context,
    barrierDismissible: false, // user must tap button!
    builder: (BuildContext context) {
      return AlertDialog(
        title: const Text('Add data:'),
        content: TextField(
          controller: myController,
        ),
        actions: <Widget>[
          TextButton(
            child: const Text('Add'),
            onPressed: () {
              addNote(myController.text.toString());
              refreshNotes();
              Navigator.of(context).pop();
            },
          ),
        ],
      );
    },
  );
}
  @override
  Widget build(BuildContext context) {
  var data = ["Today","30th June","27 May","22 May" , "7 jan"];

    return
      Scaffold(

        body:SingleChildScrollView(
          child: Container(
            width: double.infinity,
            // height: 600,
            child: ListView.builder(
              shrinkWrap: true,
              scrollDirection: Axis.vertical,
              physics: NeverScrollableScrollPhysics(),
              itemBuilder: (context,index){

              return Column(
                children: [
                  GestureDetector(
                      onTap: (){
                        deleteAll();
                      },
                      child: Text("delete")),
                  Container(
                    height: 40,
                    color:  new HexColor("#29A331").withOpacity(0.1),
                    child: Align(
                    alignment: Alignment.centerLeft,
                        child: Padding(
                          padding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 15.0),
                          child: Text(notesDates[index], style: TextStyle(color: new HexColor("#29A331") ,
                              fontSize: 17,
                            fontWeight: FontWeight.bold,
                              ),
                          ),
                        )
                    ),
                  ),
               //NESTED LIST STARTS HERE:-

               NestedList(date: notesDates[index]),
               //  NotesDatabase.instance.messageList(notesDates[index])

                ],
              );
            },
            itemCount: notesDates.length,),
          ),
        ),
          floatingActionButton: new FloatingActionButton(
              elevation: 0.0,
              child: new Icon(Icons.add),
              backgroundColor: new HexColor("#29A331"),
              onPressed: (){
                createDialogue();
              }
          )
      );

  }

}
class NestedList extends StatefulWidget {
  NestedList({ required this.date});
  final date;

  @override
  State<NestedList> createState() => _NestedListState();
}

class _NestedListState extends State<NestedList> {

var mylist ;
  @override
  Widget build(BuildContext context) {

    // dataMap = NotesDatabase.instance.readNotebyDate(date);
    return SizedBox(
      child: StreamBuilder(
        stream:  NotesDatabase.instance.readNotebyDate(widget.date).asStream(),
        builder: (context, snapshot) {
          if (!snapshot.hasData) {
            return Center(
              child: CircularProgressIndicator(),
            );
          }
          else{
            var note = NotesDatabase.instance.readNotebyDate(widget.date);
             mylist = snapshot.data;
            // return Text(snapshot.data.toString());

            return Container(

              width: double.infinity,
              child: ListView.builder(
                shrinkWrap: true,
                physics: NeverScrollableScrollPhysics(),
                itemCount: mylist.length,
                itemBuilder: (BuildContext context, int index) {
                  return Column(
                    children: [
                      ListTile(
                        title: Text(mylist[index]['title'].toString()),

                        // subtitle: Text(myList[index].subtitle),
                      ),
                      Divider()
                    ],
                  );
                },
              ),
            );

          }



            // return Text(snapshot.data[]);
            // return ListView(
            //
            //   children: snapshot.data!.docs.map((doc) {
            //     return Card(
            //
            //       child: Padding(
            //
            //         padding: EdgeInsets.all(10),
            //         child: Row(
            //           mainAxisAlignment: MainAxisAlignment.spaceBetween ,
            //           children: [
            //             Expanded(
            //                 flex: 3,
            //                 child: Text(doc['name'].toString())),
            //             Expanded(
            //                 flex: 1,
            //                 child: Text(doc['price'].toString()+" AED")),
            //
            //           ],
            //         ),
            //       ),
            //     );
            //   }).toList(),
            // );
        },
      ),
    );
  }
}


