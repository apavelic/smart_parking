using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SmartParkingWeb.Core.Models
{
    public class ChartViewModel
    {
        public Dictionary<int, Dictionary<double, int>> MultipleHighchartsData { get; set; }
        public Dictionary<double, double> HistoricalHighchartsData { get; set; }

        public ChartViewModel()
        {
            MultipleHighchartsData = new Dictionary<int, Dictionary<double, int>>();
            HistoricalHighchartsData = new Dictionary<double, double>();
        }
    }
}
