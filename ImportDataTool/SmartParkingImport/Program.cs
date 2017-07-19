using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MongoDB.Bson;
using MongoDB.Driver;

namespace SmartParkingImport
{
    class Program
    {
        private static IMongoClient client;
        private static IMongoDatabase database;

        private static readonly DateTime START_DATE = new DateTime(2017, 4, 1, 12, 0, 0);
        private static readonly DateTime END_DATE = new DateTime(2017, 7, 18, 12, 0, 0);

        private static readonly int[] PARKING_IDS = new int[] { 1, 2, 3, 4 };
        private static readonly int MIN = 1;
        private static readonly int MAX = 500;

        private static Random random;

        static void Main(string[] args)
        {
            client = new MongoClient();
            database = client.GetDatabase("SmartParking");
            random = new Random(10000);

            InsertInitialData();
        }

        private static void InsertInitialData()
        {
            var collection = database.GetCollection<BsonDocument>("state");

            foreach (int id in PARKING_IDS)
            {
                DateTime startDate = START_DATE;

                while (startDate <= END_DATE)
                {
                    startDate = startDate.AddMinutes(random.Next(20));

                    BsonDocument document = new BsonDocument
                    {
                        { "parkingId", id },
                        { "state", "NOTFREE" },
                        { "date", startDate }
                    };

                    collection.InsertOne(document);
                    int minutes = random.Next(MIN, MAX);
                    startDate = startDate.AddMinutes(minutes);

                    document = new BsonDocument
                    {
                        { "parkingId", id },
                        { "state", "FREE" },
                        { "date", startDate }
                    };

                    collection.InsertOne(document);
                }
            }
        }
    }
}
