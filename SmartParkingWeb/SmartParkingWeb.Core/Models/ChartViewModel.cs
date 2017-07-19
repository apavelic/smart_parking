using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SmartParkingWeb.Core.Models
{
    public class ChartViewModel
    {
        public Dictionary<int,Dictionary<DateTime, int>> ParkingStateDictionary { get; set; }

        public ChartViewModel()
        {
            
        }
    }
}
