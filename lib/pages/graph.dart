import 'package:flutter/material.dart';
import 'package:syncfusion_flutter_charts/charts.dart';
import 'package:intl/intl.dart';
import '../db/Notes_database.dart';

class Graph extends StatefulWidget {
  const Graph({Key? key}) : super(key: key);

  @override
  State<Graph> createState() => _GraphState();
}

class _GraphState extends State<Graph> with TickerProviderStateMixin{
  late TooltipBehavior _tooltipBehavior;
  late List<int> weekdatesData;
  late List<int> monthDatesData;
  late List<int> yearDatesData;
  late double average;
  late String dateRange;
  late TabController _tabController;

  @override
  void initState() {
    super.initState();
    _tooltipBehavior = TooltipBehavior(enable: true);
    weekdatesData = List<int>.filled(7, 0);
    monthDatesData = List<int>.filled(30, 0); // Assuming 30 days for a month
    yearDatesData = List<int>.filled(12, 0); // Assuming 12 months for a year
    average = 0.0;
    dateRange = calculateCurrentWeekDateRange();
    _tabController = TabController(length: 3, vsync: this);
    getDatesData();
    _tabController.addListener(_handleTabSelection); // Listen for tab selection
  }
  @override
  void dispose() {
    _tabController.dispose(); // Dispose the tab controller
    super.dispose();
  }
  Future<void> getDatesData() async {
    print('loadinggg........');

    List<String> previousWeekDates = getPreviousWeekDates();
    var avgCount = 0;

    for (int i = 0; i < previousWeekDates.length; i++) {
      int noteCount = await NotesDatabase.instance.getDocumentCountByDate(previousWeekDates[i]);
      print("Count for ${previousWeekDates[i]}: $noteCount");
      weekdatesData[i] = noteCount;
      avgCount += noteCount;
      print("fayes: " + noteCount.toString());
    }

    // Calculate monthly and yearly data similarly

    setState(() {
      average = avgCount / 7;
    });
  }
  void _handleTabSelection() {
    setState(() {
      // Update the data and date range based on the selected tab
      if (_tabController.index == 0) {
        dateRange = calculateCurrentWeekDateRange();
        // Load weekly data here
      } else if (_tabController.index == 1) {
        dateRange = calculateCurrentMonthDateRange();
        // Load monthly data here
      } else if (_tabController.index == 2) {
        dateRange = calculateCurrentYearDateRange();
        // Load yearly data here
      }
    });
  }

  List<String> getPreviousWeekDates() {
    List<String> previousWeekDates = [];
    DateTime currentDate = DateTime.now();
    DateTime previousWeekStartDate = currentDate.subtract(Duration(days: 6));

    for (int i = 0; i < 7; i++) {
      DateTime date = previousWeekStartDate.add(Duration(days: i));
      String formattedDate = DateFormat('MMMM d, y').format(date);
      previousWeekDates.add(formattedDate);
    }

    return previousWeekDates;
  }

  String calculateCurrentWeekDateRange() {
    DateTime currentDate = DateTime.now();
    DateTime firstDayOfWeek = currentDate.subtract(Duration(days: currentDate.weekday - 1));
    DateTime lastDayOfWeek = firstDayOfWeek.add(Duration(days: 6));
    String formattedFirstDay = DateFormat('d MMMM').format(firstDayOfWeek);
    String formattedLastDay = DateFormat('d MMMM').format(lastDayOfWeek);
    return '$formattedFirstDay - $formattedLastDay';
  }

  String calculateCurrentMonthDateRange() {
    DateTime currentDate = DateTime.now();
    DateTime firstDayOfMonth = DateTime(currentDate.year, currentDate.month, 1);
    DateTime lastDayOfMonth = DateTime(currentDate.year, currentDate.month + 1, 0);
    String formattedFirstDay = DateFormat('d MMMM').format(firstDayOfMonth);
    String formattedLastDay = DateFormat('d MMMM').format(lastDayOfMonth);
    return '$formattedFirstDay - $formattedLastDay';
  }

  String calculateCurrentYearDateRange() {
    DateTime currentDate = DateTime.now();
    DateTime firstDayOfYear = DateTime(currentDate.year, 1, 1);
    DateTime lastDayOfYear = DateTime(currentDate.year, 12, 31);
    String formattedFirstDay = DateFormat('d MMMM').format(firstDayOfYear);
    String formattedLastDay = DateFormat('d MMMM').format(lastDayOfYear);
    return '$formattedFirstDay - $formattedLastDay';
  }

  // ... Rest of your code for fetching and displaying data ...

  @override
  Widget build(BuildContext context) {
    List<ChartData> chartData;

    if (_tabController.index == 0) {
      chartData = weekdatesData
          .asMap()
          .entries
          .map((entry) => ChartData('Day ${entry.key}', entry.value))
          .toList();
    } else if (_tabController.index == 1) {
      chartData = monthDatesData
          .asMap()
          .entries
          .map((entry) => ChartData('Day ${entry.key}', entry.value))
          .toList();
    } else {
      chartData = yearDatesData
          .asMap()
          .entries
          .map((entry) => ChartData('Month ${entry.key}', entry.value))
          .toList();
    }

    return SafeArea(
      child: Scaffold(
        body: Column(
          children: [
            SizedBox(height: 19,),
            TabBar(
              controller: _tabController, // Connect the TabBar to the TabController
              indicator: BoxDecoration(), // Set an empty BoxDecoration for the indicator
              tabs: [
                Tab(
                  child: Container(
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(10),
                      border: Border.all(color: Colors.grey),
                    ),
                    child: Center(
                      child: Text(
                        'W',
                        style: TextStyle(
                          color: Colors.blue,
                        ),
                      ),
                    ),
                  ),
                ),
                Tab(
                  child: Container(
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(10),
                      border: Border.all(color: Colors.grey),
                    ),
                    child: Center(
                      child: Text(
                        'M',
                        style: TextStyle(
                          color: Colors.blue,
                        ),
                      ),
                    ),
                  ),
                ),
                Tab(
                  child: Container(
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(10),
                      border: Border.all(color: Colors.grey),
                    ),
                    child: Center(
                      child: Text(
                        'Y',
                        style: TextStyle(
                          color: Colors.blue,
                        ),
                      ),
                    ),
                  ),
                ),
              ],
            ),
            // Your UI widgets here

            Container(
              margin: EdgeInsets.all(20),
              child: SizedBox(
                height: 20,

              ),
            ),
            Container(
              child: Row(
                children: [
                  SizedBox(width: 20,),
                  Text(average.toStringAsFixed(1), style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold)),
                  SizedBox(width: 8,),
                  Text('fucks given on average', style: TextStyle(fontSize: 20)),
                ],
              ),
            ),
            SizedBox(height: 5,),
            Container(
              child: Row(
                children: [
                  SizedBox(width: 20,),
                  Text(dateRange, style: TextStyle(fontSize: 17)),
                ],
              ),
            ),
            SizedBox(height: 5,),
            Container(
              child: SfCartesianChart(
                primaryXAxis: CategoryAxis(),
                series: <ChartSeries>[
                  LineSeries<ChartData, String>(
                    color: Colors.blue, // Change the color as needed
                    dataSource: chartData,
                    xValueMapper: (ChartData data, _) => data.x,
                    yValueMapper: (ChartData data, _) => data.y!,
                    markerSettings: MarkerSettings(
                      isVisible: true,
                      shape: DataMarkerType.circle,
                    ),
                  )
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class ChartData {
  ChartData(this.x, this.y);
  final String x;
  final int? y;
}
